tree grammar VSLTreeParser;

options {
	language	 = Java;
	tokenVocab   = VSLParser;
	ASTLabelType = CommonTree;
	output	   = AST;
}


s [SymbolTable symTab] returns [Code3a code]
	: program[symTab]
		{
			// Return the code (displayed into VclComp)
			$code = $program.code;
		}
;


program [SymbolTable symTab] returns [Code3a code]
	: ^(PROG unit[symTab]+)
		{
			// Just transfer the code
			$code = $unit.code;
		}
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
	: ^(FUNC_KW type IDENT param_list[symTab] ^(BODY statement[symTab]))
		{
			// Get the ident from the symtab
			Operand3a id = $symTab.lookup($IDENT.text);

			// If the ident is already defined
			if (id != null) Errors.redefinedIdentifier($IDENT, $IDENT.text, null);

			// The function type
			FunctionType functionType = new FunctionType($type.type);

			// Create the function symbol
			LabelSymbol functionLabel = new LabelSymbol($IDENT.text);
			FunctionSymbol functionSymbol = new FunctionSymbol(functionLabel, functionType);

			// Add it to tabSymb
			$symTab.insert($IDENT.text, functionSymbol);

			// Generate its code
			$code = Code3aGenerator.genFunction(functionSymbol, $statement.code);
		}
;


proto [SymbolTable symTab] returns [Code3a code]
	: ^(PROTO_KW type IDENT LP! param_list[symTab] RP!)
		{
			// TODO: Here we should add the proto to symTab

			$code = $param_list.code;
		}
;


type returns [Type type]
	: INT_KW^
		{
			// Just return the int type
			$type = Type.INT;
		}

	| VOID_KW^
		{
			// Just return the void type
			$type = Type.VOID;
		}
;


param_list [SymbolTable symTab] returns [Code3a code]
	: ^(PARAM param[symTab]*)
		{
			$code = $param.code;
		}

	| PARAM^
		{
			// Just an empty code because emtpy word
			$code = new Code3a();
		}
;


param [SymbolTable symTab] returns [Code3a code]
	: ^(ARRAY IDENT)
		{
			$code = new Code3a();
		}
;


statement [SymbolTable symTab] returns [Code3a code]
	: ^(ASSIGN_KW IDENT expression[symTab])
		{
			// Get the ident from the symtab
			Operand3a id = symTab.lookup($IDENT.text);

			// If the ident wasn't found
			if (id == null) Errors.unknownIdentifier($ASSIGN_KW, $IDENT.text, null);

			// Check expression and IDENT type
			Type ty = TypeCheck.checkBinOp(id.type, $expression.expAtt.type);

			// If wrong type
			if (ty == Type.ERROR) Errors.incompatibleTypes($ASSIGN_KW, id.type, ty, null);

			// And here, affect
			$code = Code3aGenerator.genAff(id, $expression.expAtt);
		}

	| RETURN_KW^ expression[symTab]
		{
			// Put the return of the function
			$code = Code3aGenerator.genReturn($expression.expAtt);
		}

	| PRINT_KW^ print_list[symTab]
		{
			// Put the returned code
			$code = $print_list.code;
		}

	| READ_KW^ read_list[symTab]
		{
			// Put the returned code
			$code = $read_list.code;
		}

	| ^(IF_KW expression[symTab] THEN_KW! st1=statement[symTab] (ELSE_KW! st2=statement[symTab])? FI_KW!)
		{
			// If there's a second statement
			if (st2 != null) $code = Code3aGenerator.genIFELSE($expression.expAtt, $st1.code, $st2.code);

			// If no second statement
			else $code = Code3aGenerator.genIF($expression.expAtt, $st1.code);
		}

	| ^(WHILE_KW expression[symTab] DO_KW! st=statement[symTab] OD_KW!)
		{
			// Generate the while code
			$code = Code3aGenerator.genWHILE($expression.expAtt, $st.code);
	 	}

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
			$code = $expression.expAtt.code;
		}
;


inst_list [SymbolTable symTab] returns [Code3a code]
	: ^(INST (statement[symTab])+)
		{
			$code = $statement.code;
		}

	| ^(DECL (decl_item[symTab])+)
		{
			$code = $decl_item.code;
		}
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
			System.err.println("Factor value = " + $factor.expAtt);
			$expAtt = $factor.expAtt;
		}
;


factor [SymbolTable symTab] returns [ExpAttribute expAtt]
	: ^(MUL p1=factor[symTab] p2=factor[symTab])
		{
			// We get the result of check type
			Type ty = TypeCheck.checkBinOp($p1.expAtt.type, $p2.expAtt.type);

			// If wrong type
			if (ty == Type.ERROR) Errors.incompatibleTypes($MUL, Type.INT, ty, null);

			// Get a new temporary var for the 3ad code
			VarSymbol temp = SymbDistrib.newTemp();
		 
			// Return the Expression attribute
			$expAtt = new ExpAttribute(ty, Code3aGenerator.genBinOp(Inst3a.TAC.MUL, temp, $p1.expAtt, $p2.expAtt), temp);
		}
	
	| ^(DIV p1=factor[symTab] p2=factor[symTab])
		{
			// We get the result of check type
			Type ty = TypeCheck.checkBinOp($p1.expAtt.type, $p2.expAtt.type);

			// If wrong type
			if (ty == Type.ERROR) Errors.incompatibleTypes($DIV, Type.INT, ty, null);

			// Get a new temporary var for the 3ad code
			VarSymbol temp = SymbDistrib.newTemp();

			// Return the Expression attribute
			$expAtt = new ExpAttribute(ty, Code3aGenerator.genBinOp(Inst3a.TAC.DIV, temp, $p1.expAtt, $p2.expAtt), temp);
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
			Operand3a id = symTab.lookup($IDENT.text);

			// If the ident wasn't found
			if (id == null) Errors.unknownIdentifier($IDENT, $IDENT.text, null);

			// Return the ExpAttribute corresponding to it
			$expAtt = new ExpAttribute(id.type, new Code3a(), id);
		}

	| ^(NEGAT p=primary[symTab])
		{
			// Get a new temporary var for the 3a code
			VarSymbol temp = SymbDistrib.newTemp();

			// Return the ExpAttribute corresponding to it
			$expAtt = new ExpAttribute($p.expAtt.type, Code3aGenerator.genUnaryOp(Inst3a.TAC.NEG, temp, $p.expAtt), temp);
		}
;


argument_list [SymbolTable symTab] returns [Code3a code]
	: e1=expression[symTab] (COM! e2=expression[symTab])*
		{
			// Get the code of each expression and append them
			$code = Code3aGenerator.concatenateCodes($e1.expAtt.code, $e2.expAtt.code);
		}
;


print_list [SymbolTable symTab] returns [Code3a code]
	: p1=print_item[symTab] (COM! p2=print_item[symTab])*
		{
			// Get the code of each print item and append them
			$code = Code3aGenerator.concatenateCodes($p1.code, $p2.code);
		}
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
	: r1=read_item[symTab] (COM! r2=read_item[symTab])*
		{
			// Get the code of each read item and append them
			$code = Code3aGenerator.concatenateCodes($r1.code, $r2.code);
		}
;


read_item [SymbolTable symTab] returns [Code3a code]
	: IDENT
		{
			// Get the ident from the symtab && If the ident wasn't found
			if (symTab.lookup($IDENT.text) == null) System.err.println("La variable n'est pas déclarée dans la table des symbole");

			// And here, read (with 3@ code)
			$code = Code3aGenerator.genReadInteger((VarSymbol)symTab.lookup($IDENT.text));
		}
	| array_elem[symTab]
		{
			// TODO
		}
;


declaration [SymbolTable symTab] returns [Code3a code]
	: ^(DECL decl_list[symTab]+)
		{
			// Get the code and append it
			$code = $decl_list.code;
		}
;


decl_list [SymbolTable symTab] returns [Code3a code]
	: d1=decl_item[symTab] (COM! d2=decl_item[symTab])*
		{
			System.err.println("Code de d1" + $d1.code);
			System.err.println("Code de d2" + $d2.code);

			// Just append the generated codes
			$code = Code3aGenerator.concatenateCodes($d1.code, $d2.code);
		}
;


decl_item [SymbolTable symTab] returns [Code3a code]
	: IDENT
		{
			//System.err.println("Declarations de " + $IDENT.text);

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
			// TODO
			$code = new Code3a();
		}
;