tree grammar VSLTreeParser;

options {
	language	 = Java;
	tokenVocab   = VSLParser;
	ASTLabelType = CommonTree;
	output	     = AST;
}


s [SymbolTable symTab] returns [Code3a code]
	: program[symTab]
		{
			// Return the code (displayed into VclComp)
			$code = $program.code;
		}
;


program [SymbolTable symTab] returns [Code3a code]
	: ^(PROG  { Code3a c = new Code3a(); }  (unit[symTab] { c.append($unit.code); } )+  { $code = c; } )
;


unit [SymbolTable symTab] returns [Code3a code]
	: function[symTab]
		{
			// Just transfer the code
			$code = $function.code;
		}

	| proto[symTab]
		{
			// Just transfer the code
			$code = $proto.code;
		}
;


function [SymbolTable symTab] returns [Code3a code]
	: ^(FUNC_KW type IDENT  { FunctionType functionType = new FunctionType($type.type, false); }  param_list[symTab, functionType] ^(BODY statement[symTab])
			{
				// Get the ident from the symtab
				Operand3a id = $symTab.lookup($IDENT.text);

				// If the ident is already defined
				if ((id != null) && (!functionType.isCompatible(id.type))) Errors.redefinedIdentifier($IDENT, $IDENT.text, null);

				// Create the function symbol
				LabelSymbol functionLabel = new LabelSymbol($IDENT.text);
				FunctionSymbol functionSymbol = new FunctionSymbol(functionLabel, functionType);

				// Add it to tabSymb
				$symTab.insert($IDENT.text, functionSymbol);

				// Generate its code
				$code = Code3aGenerator.genFunction(functionSymbol, $param_list.code, $statement.code);
			}
		)
;


proto [SymbolTable symTab] returns [Code3a code]
    // TRUE INSTEAD OF FLASE BECAUSE IS prototype
	: ^(PROTO_KW type IDENT  { FunctionType functionType = new FunctionType($type.type, true); }  param_list[symTab, functionType]
		{
			// Get the ident from the symtab
			Operand3a id = $symTab.lookup($IDENT.text);

			// If the ident is already defined
			if (id != null) Errors.redefinedIdentifier($IDENT, $IDENT.text, null);

			// Create the function symbol
			LabelSymbol functionLabel = new LabelSymbol($IDENT.text);
			FunctionSymbol functionSymbol = new FunctionSymbol(functionLabel, functionType);

			// Add it to tabSymb
			$symTab.insert($IDENT.text, functionSymbol);

			// No code, just a prototype added to the tabSymb
			$code = new Code3a();
		}
	)
;


type returns [Type type]
	: INT_KW
		{
			// Just return the int type
			$type = Type.INT;
		}

	| VOID_KW
		{
			// Just return the void type
			$type = Type.VOID;
		}
;


param_list [SymbolTable symTab, FunctionType functionType] returns [Code3a code]
	: ^(PARAM  { Code3a c = new Code3a(); }  ( param[symTab, functionType] { c.append($param.code); } )*  { $code = c; } )

;


param [SymbolTable symTab, FunctionType functionType] returns [Code3a code]
	: ^(ARRAY IDENT)
		{
			// Add this param to the symTab of this function or prototype
			VarSymbol vs = new VarSymbol(Type.POINTER, $IDENT.text, $symTab.getScope());

			// But first, we say that this is a parameter
			vs.setParam();
			$symTab.insert($IDENT.text, vs);

			// Add the parameter to the functionType list
			functionType.extend(Type.POINTER);

			// TODO: Build the code for the array
			$code = new Code3a();
		}

	| 	IDENT
		{
			// Add this param to the symTab of this function or prototype
			VarSymbol vs = new VarSymbol(Type.INT, $IDENT.text, $symTab.getScope());

			// But first, we say that this is a parameter
			vs.setParam();
			$symTab.insert($IDENT.text, vs);

			// Add the parameter to the functionType list
			functionType.extend(Type.INT);

			// Just generate the code corresponding to an ident param
			$code = new Code3a(new Inst3a(Inst3a.TAC.VAR, new VarSymbol(Type.INT, $IDENT.text, $symTab.getScope()), null, null));
		}
;


statement [SymbolTable symTab] returns [Code3a code]
	: ^(ASSIGN_KW expression[symTab]
		( IDENT
			{
				// Get the ident from the symtab
				Operand3a id = $symTab.lookup($IDENT.text);

				// If the ident wasn't found
				if (id == null) Errors.unknownIdentifier($ASSIGN_KW, $IDENT.text, null);

				// Check expression and IDENT type
				Type ty = TypeCheck.checkBinOp(id.type, $expression.expAtt.type);

				// If wrong type
				if (ty == Type.ERROR) Errors.incompatibleTypes($ASSIGN_KW, id.type, ty, null);

				// And here, affect
				$code = Code3aGenerator.genAff(id, $expression.expAtt);
			}

		| array_elem[symTab]
			{
				// check the INDEX's type
				if(!($expression.expAtt.type.isCompatible(Type.INT))) Errors.incompatibleTypes($ASSIGN_KW, Type.INT, $expression.expAtt.type, null);

				$code = Code3aGenerator.genAffarray($array_elem.code, $expression.expAtt);
			}
		)
	)

	| ^(RETURN_KW expression[symTab])
		{
			// Put the return of the function
			$code = Code3aGenerator.genReturn($expression.expAtt);
		}

	| ^(PRINT_KW print_list[symTab])
		{
			// Put the returned code
			$code = $print_list.code;
		}

	| ^(READ_KW read_list[symTab])
		{
			// Put the returned code
			$code = $read_list.code;
		}

	| ^(IF_KW expression[symTab]  st1=statement[symTab] (st2=statement[symTab])? )
		{
			// If there's a second statement
			if (st2 != null) $code = Code3aGenerator.genIFELSE($expression.expAtt, $st1.code, $st2.code);

			// If no second statement
			else $code = Code3aGenerator.genIF($expression.expAtt, $st1.code);
		}

	| ^(WHILE_KW expression[symTab] st=statement[symTab])
		{
			// Generate the while code
			$code = Code3aGenerator.genWHILE($expression.expAtt, $st.code);
		}

	| ^(FCALL_S IDENT  { FunctionType expectedType = new FunctionType(Type.VOID); }  argument_list[symTab, expectedType]?
			{
				// We check that the function is defined
				Operand3a function = $symTab.lookup($IDENT.text);
				if (function == null) Errors.unknownIdentifier($IDENT, $IDENT.text, null);

				// Check that the function is a function typed
				if (!(function instanceof FunctionSymbol)) Errors.unknownIdentifier($IDENT, $IDENT.text, "Label not known as a function");

				// We check also that it's a function
				if (!function.type.isCompatible(expectedType)) {

					// If not a VOID one, it can be an INT one
					FunctionType maybeInt = new FunctionType(Type.INT);
					for (Type t : expectedType.getArguments()) {
						maybeInt.extend(t);
					}

					// Check with INT one
					if (!function.type.isCompatible(maybeInt))
						Errors.incompatibleTypes($FCALL_S, expectedType, function.type, "Waited a function, no matter its return value");
				}

				// We build the code from extern method
				$code = Code3aGenerator.genProcedureCall($IDENT.text, $argument_list.code);
			}
		)

	| block[symTab]
		{
			// And we return the code
			$code = $block.code;

			// We leave the block so we quit the scope
			$symTab.leaveScope();
		}
;


block [SymbolTable symTab] returns [Code3a code]
	: ^(BLOCK declaration[symTab] inst_list[symTab])
		{
			// Enter the block scope
			$symTab.enterScope();

			// Just return the code appended to the actual one
			$code = Code3aGenerator.concatenateCodes($declaration.code, $inst_list.code);
		}

	| ^(BLOCK inst_list[symTab])
		{
			// Enter the block scope
			$symTab.enterScope();

			// Just return the code of the instruction's list
			$code = $inst_list.code;
		}
;


array_elem [SymbolTable symTab] returns [Code3a code]
	: ^(ARELEM IDENT expression[symTab])
		{
			// Get the ident from the symtab
			Operand3a id = $symTab.lookup($IDENT.text);

            // If the ident wasn't found
			if (id == null) Errors.unknownIdentifier($IDENT, $IDENT.text, null);

			// check the IDENT's type
			if(!(id.type.isCompatible(Type.POINTER))) Errors.incompatibleTypes($IDENT, Type.POINTER, id.type, null);

			// check the INDEX's type
			if(!($expression.expAtt.type.isCompatible(Type.INT))) Errors.incompatibleTypes($ARELEM, Type.INT, $expression.expAtt.type, null);

			// Return The code
			$code = Code3aGenerator.genArrayElem(id , $expression.expAtt);
		}
;


inst_list [SymbolTable symTab] returns [Code3a code]
	: ^(INST  { Code3a c = new Code3a(); }  (st=statement[symTab]  { c.append(Code3aGenerator.genInstruction($st.code)); }  )+  { $code = c; } )
;


expression [SymbolTable symTab] returns [ExpAttribute expAtt]
	: ^(PLUS e1=expression[symTab] e2=expression[symTab])
		{
			// We get the result of check type
			Type ty = TypeCheck.checkBinOp($e1.expAtt.type, $e2.expAtt.type);

			// If wrong type
			if (ty == Type.ERROR) Errors.incompatibleTypes($PLUS, Type.INT, ty, null);

			// Get a new temporary var for the 3ad code
			VarSymbol temp = SymbDistrib.newTemp();

			// Return the Expression attribute
			$expAtt = new ExpAttribute(ty, Code3aGenerator.genBinOp(Inst3a.TAC.ADD, temp, $e1.expAtt, $e2.expAtt), temp);
		}

	| ^(MINUS e1=expression[symTab] e2=expression[symTab])
		{
			// We get the result of check type
			Type ty = TypeCheck.checkBinOp($e1.expAtt.type, $e2.expAtt.type);

			// If wrong type
			if (ty == Type.ERROR) Errors.incompatibleTypes($MINUS, Type.INT, ty, null);

			// Get a new temporary var for the 3ad code
			VarSymbol temp = SymbDistrib.newTemp();

			// Return the Expression attribute
			$expAtt = new ExpAttribute(ty, Code3aGenerator.genBinOp(Inst3a.TAC.SUB, temp, $e1.expAtt, $e2.expAtt), temp);
		}

	| factor[symTab]
		{
			$expAtt = $factor.expAtt;
		}
;


factor [SymbolTable symTab] returns [ExpAttribute expAtt]
	: ^(MUL f1=factor[symTab] f2=factor[symTab])
		{
			// We get the result of check type
			Type ty = TypeCheck.checkBinOp($f1.expAtt.type, $f2.expAtt.type);

			// If wrong type
			if (ty == Type.ERROR) Errors.incompatibleTypes($MUL, Type.INT, ty, null);

			// Get a new temporary var for the 3ad code
			VarSymbol temp = SymbDistrib.newTemp();

			// Return the Expression attribute
			$expAtt = new ExpAttribute(ty, Code3aGenerator.genBinOp(Inst3a.TAC.MUL, temp, $f1.expAtt, $f2.expAtt), temp);
		}

	| ^(DIV f1=factor[symTab] f2=factor[symTab])
		{
			// We get the result of check type
			Type ty = TypeCheck.checkBinOp($f1.expAtt.type, $f2.expAtt.type);

			// If wrong type
			if (ty == Type.ERROR) Errors.incompatibleTypes($DIV, Type.INT, ty, null);

			// Get a new temporary var for the 3ad code
			VarSymbol temp = SymbDistrib.newTemp();

			// Return the Expression attribute
			$expAtt = new ExpAttribute(ty, Code3aGenerator.genBinOp(Inst3a.TAC.DIV, temp, $f1.expAtt, $f2.expAtt), temp);
		}

	| primary[symTab]
		{
			$expAtt = $primary.expAtt;
		}
;


primary [SymbolTable symTab] returns [ExpAttribute expAtt]
	: INTEGER
		{
			// Create a const symbol 3a code and just put the integer value
			ConstSymbol cs = new ConstSymbol(Integer.parseInt($INTEGER.text));
			$expAtt = new ExpAttribute(Type.INT, new Code3a(), cs);
		}

	| IDENT
		{
			// Get the ident from the symtab
			Operand3a id = $symTab.lookup($IDENT.text);

			// If the ident wasn't found
			if (id == null) Errors.unknownIdentifier($IDENT, $IDENT.text, null);

			// Return the ExpAttribute corresponding to it
			$expAtt = new ExpAttribute(id.type, new Code3a(), id);
		}

	| array_elem[symTab]
		{



		}

	| ^(FCALL IDENT  { FunctionType expectedType = new FunctionType(Type.INT); }  argument_list[symTab, expectedType]?
			{
				// We check that the function is defined
				Operand3a function = $symTab.lookup($IDENT.text);
				if (function == null) Errors.unknownIdentifier($IDENT, $IDENT.text, null);

				// Check that the function is a function typed
				if (!(function instanceof FunctionSymbol)) Errors.unknownIdentifier($IDENT, $IDENT.text, "Label not known as a function");

				// We check also that it's a function
				if (!function.type.isCompatible(expectedType)) Errors.incompatibleTypes($FCALL, expectedType, function.type, "");

				// We build ExpAtt from extern method
				$expAtt = Code3aGenerator.genFunctionCall($IDENT.text, (FunctionType)function.type, $argument_list.code);
			}
		)

	| LP! expression[symTab] RP!
		{
		   	$expAtt = $expression.expAtt;
		}

	| ^(NEGAT p=primary[symTab])
		{
			// Get a new temporary var for the 3a code
			VarSymbol temp = SymbDistrib.newTemp();

			// Return the ExpAttribute corresponding to it
			$expAtt = new ExpAttribute($p.expAtt.type, Code3aGenerator.genUnaryOp(Inst3a.TAC.NEG, temp, $p.expAtt), temp);
		}
;


argument_list [SymbolTable symTab, FunctionType expectedType] returns [Code3a code]
	: { Code3a c = new Code3a(); }  ( expression[symTab]  { c = Code3aGenerator.genArg($expression.expAtt); expectedType.extend($expression.expAtt.type); }  )+  { $code = c; }
;


print_list [SymbolTable symTab] returns [Code3a code]
	: { Code3a c = new Code3a(); }  ( print_item[symTab] { c.append($print_item.code); } )+  { $code = c; }
;


print_item [SymbolTable symTab] returns [Code3a code]
	: TEXT
		{
			// Generate the code to print a string
		   $code = Code3aGenerator.genPrintString($TEXT.text);
		}

	| expression[symTab]
		{
			// Generate the code to print the string from the result of the expression
			$code = Code3aGenerator.genPrintInteger($expression.expAtt);
		}
;


read_list [SymbolTable symTab] returns [Code3a code]
	: { Code3a c = new Code3a(); }  ( read_item[symTab] { c.append($read_item.code); } )+  { $code = c; }
;


read_item [SymbolTable symTab] returns [Code3a code]
	: IDENT
		{
			// Get the ident from the symtab && If the ident wasn't found
			if ($symTab.lookup($IDENT.text) == null) Errors.unknownIdentifier($IDENT, $IDENT.text, "");

			// And here, read (with 3@ code)
			$code = Code3aGenerator.genReadInteger((VarSymbol)symTab.lookup($IDENT.text));
		}

	| array_elem[symTab]
		{
			// TODO
		}
;


declaration [SymbolTable symTab] returns [Code3a code]
	: ^(DECL { Code3a code1 = new Code3a(); }  ( decl_item[symTab]  { code1.append($decl_item.code); } )+  { $code = code1; } )
;


decl_item [SymbolTable symTab] returns [Code3a code]
	: IDENT
		{
			// Get the ident from the symtab
			Operand3a id = $symTab.lookup($IDENT.text);

			// If the ident is already defined
			if (id != null) Errors.redefinedIdentifier($IDENT, $IDENT.text, null);

			// Code generated to declare a variable (var [varname])
			VarSymbol declaredVar = new VarSymbol(Type.INT, $IDENT.text, $symTab.getScope());  // Only integers

			// Then add the var to the actual tabSymb scope
			$symTab.insert($IDENT.text, declaredVar);

			// Generate the code
			$code = Code3aGenerator.genVarDeclaration(declaredVar);
		}

	| ^(ARDECL IDENT INTEGER)
		{

			// Get the ident from the symtab
			Operand3a id = $symTab.lookup($IDENT.text);

			// If the ident of array is already defined
			if (id != null) Errors.redefinedIdentifier($IDENT, $IDENT.text, null);

			// Code generated to declare a array (var [varname])
			VarSymbol declaredArray = new VarSymbol(Type.POINTER, $IDENT.text, $symTab.getScope());  // Only integers

			// Then add the var to the actual tabSymb scope
			$symTab.insert($IDENT.text, declaredArray);

			// Generate the code
			$code = Code3aGenerator.genVarDeclaration(declaredArray);
		}
;
