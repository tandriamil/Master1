tree grammar VSLTreeParser;

options {
	language	 = Java;
	tokenVocab   = VSLParser;
	ASTLabelType = CommonTree;
	output       = AST;
}


s [SymbolTable symTab]
	: expression[symTab]
		{
			// Display the code
			$expression.expAtt.code.print();
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

			// And here, affect (with 3@ code)
			// code.append(Code3aGenerator.genAff(id, exp));
			$code = Code3aGenerator.genAff(id, $expression.expAtt);
		}

	| ^(IF_KW expression[symTab] THEN_KW! st1=statement[symTab] (ELSE_KW! st2=statement[symTab])? FI_KW!)
		{
			// If there's a second statement
			// if (st2 != null) code.append(Code3aGenerator.genIF(exp, st1));
			if (st2 != null) $code = Code3aGenerator.genIF($expression.expAtt, $st1.code);

			// If no second statement
			// else code.append(Code3aGenerator.genIF(exp, st1, st2));
			else $code = Code3aGenerator.genIFELSE($expression.expAtt, $st1.code, $st2.code);
		}

	| ^(WHILE_KW expression[symTab] DO_KW! st=statement[symTab] OD_KW!)
		{
			// Generate the while code
			//code.append(Code3aGenerator.genWHILE(exp, st));
			$code = Code3aGenerator.genWHILE($expression.expAtt, $st.code);
	 	}

	| block[symTab]
		{
			$code = $block.code;
		}
;

print_list [SymbolTable symTab] returns [Code3a code]
    : (p = print_item[symTab] {$code = $p;})+
    ;

print_item [SymbolTable symTab] returns [Code3a code]
    : TEXT
	    {
           $code = Code3aGenerator.genPrintString($TEXT.text);
	    }
    | exp = expression[symTab]
	    {
           $code = Code3aGenerator.genPrintInteger(exp);
	    }
    ;

read_list [SymbolTable symTab] returns [Code3a code]
    : (r = read_item[symTab] {$code = $r})+
    ;

read_item [SymbolTable symTab] returns [Code3a code]
    : IDENT
	    {
	        // Get the ident from the symtab && If the ident wasn't found
			if (symTab.lookup($IDENT.text) == null) System.err.println("La variable n'est pas déclarée dans la table des symbole");

			// And here, read (with 3@ code)
			$code = Code3aGenerator.genReadInteger((VarSymbol)symTab.lookup($IDENT.text));


	    }
    | array_elem
    ;    

block [SymbolTable symTab] returns [Code3a code]
	: ^(BLOCK  inst_list[symTab])
		{

		}

	| ^(BLOCK (declaration[symTab]) inst_list[symTab])
		{
			$code = $inst_list.code;
		}
;


inst_list [SymbolTable symTab] returns [Code3a code]
	: ^(INST statement[symTab]+)
		{
			$code = $statement.code;
		}

	| ^(DECL decl_item[symTab]+)
;


decl_item [SymbolTable symTab]
	: ^(ARDECL IDENT INTEGER)
		{
			// Get the ident from the symtab
			Operand3a id = symTab.lookup($IDENT.text);

			// If the ident is already defined
			if (id != null) Errors.redefinedIdentifier($ARDECL, $IDENT.text, null);

			// If ok, define it
			id = new Operand3a(Type.INT);

			// And add it to symTab
			$symTab.insert($IDENT.text, id);
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
		{ $expAtt = $factor.expAtt; }
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
	| pe=primary_exp[symTab]
		{ $expAtt = $pe.expAtt; }
;


primary_exp [SymbolTable symTab] returns [ExpAttribute expAtt]
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
	| ^(NEGAT pe=primary_exp[symTab])
		{
			// Get a new temporary var for the 3a code
			VarSymbol temp = SymbDistrib.newTemp();

			// Return the ExpAttribute corresponding to it
			$expAtt = new ExpAttribute($pe.expAtt.type, Code3aGenerator.genBinOp(Inst3a.TAC.NEG, temp, $pe.expAtt, null), temp);
		}
;