tree grammar VSLTreeParser;

options {
  language     = Java;
  tokenVocab   = VSLParser;
  ASTLabelType = CommonTree;
}

s [SymbolTable symTab] returns [Code3a code]
  : e=expression[symTab] { code = e.code; }
  ;


statement [SymbolTable symTab] returns [Code3a code]
    : ^(ASSIGN_KW {code = new Code3a();} IDENT exp = expression[symTab]) {

      // Get the ident from the symtab
      Operand3a id = symTab.lookup($IDENT.text);

      // If the ident wasn't found
      if (id == null) Errors.unknownIdentifier($ASSIGN_KW, $IDENT.text, null);

      // Check expression and IDENT type
      Type ty = TypeCheck.checkBinOp(id.type, exp.type);

      // If wrong type
      if (ty == Type.ERROR) {
        Errors.incompatibleTypes($ASSIGN_KW, id.type, ty, null);
      }

      // And here, affect (with 3@ code)
      code.append(Code3aGenerator.genAff(id,exp));
     } 


    | ^(IF_KW exp = expression[symTab] THEN_KW! st1 = statement[symTab] (ELSE_KW! st2 = statement[symTab])? FI_KW!) {
        if(st2 != null){
            code.append(Code3aGenerator.genIF(exp, st1));          
        }
        else{
            code.append(Code3aGenerator.genIF(exp, st1, st2));
        }
     }
    | ^(WHILE_KW exp = expression[symtab] DO_KW! st = statement[symTab] OD_KW!) {
            code.append(Code3aGenerator.genWHILE(exp, st));
     }

    ;

expression [SymbolTable symTab] returns [ExpAttribute expAtt]
  : ^(PLUS e1=expression[symTab] e2=expression[symTab]) 
    {
      // We get the type
      Type ty = TypeCheck.checkBinOp(e1.type, e2.type);
      // If wrong type
      if (ty == Type.ERROR) {
        Errors.incompatibleTypes($PLUS, Type.INT, ty, null);
      }
      // Get a new temporary var for the 3ad code
      VarSymbol temp = SymbDistrib.newTemp();
      // Return the Expression attribute
      expAtt = new ExpAttribute(ty, Code3aGenerator.genBinOp(Inst3a.TAC.ADD, temp, e1, e2), temp);
    }
  | ^(MINUS e1=expression[symTab] e2=expression[symTab]) 
    { 
      Type ty = TypeCheck.checkBinOp(e1.type, e2.type);
      // If wrong type
      if (ty == Type.ERROR) {
        Errors.incompatibleTypes($MINUS, Type.INT, ty, null);
      }
      // Get a new temporary var for the 3ad code
      VarSymbol temp = SymbDistrib.newTemp();
      // Return the Expression attribute
      expAtt = new ExpAttribute(ty, Code3aGenerator.genBinOp(Inst3a.TAC.SUB, temp, e1, e2), temp);
    }  
  | pe=factor[symTab] 
    { expAtt = pe; }
  ;
factor [SymbolTable symTab] returns [ExpAttribute expAtt]
  : ^(MUL p1=factor[symTab] p2=factor[symTab])
    { 
      // We get the type
      Type ty = TypeCheck.checkBinOp(p1.type, p2.type);
      // If wrong type
      if (ty == Type.ERROR) {
        Errors.incompatibleTypes($MUL, Type.INT, ty, null);
      }
      // Get a new temporary var for the 3ad code
      VarSymbol temp = SymbDistrib.newTemp();
     
      // Return the Expression attribute
      expAtt = new ExpAttribute(ty, Code3aGenerator.genBinOp(Inst3a.TAC.MUL, temp, p1, p2), temp);
    }
  |^(DIV p1=factor[symTab] p2=factor[symTab])
    { 
      Type ty = TypeCheck.checkBinOp(p1.type, p2.type);
      // If wrong type
      if (ty == Type.ERROR) Errors.incompatibleTypes($DIV, Type.INT, ty, null);
      // Get a new temporary var for the 3ad code
      VarSymbol temp = SymbDistrib.newTemp();
      // Return the Expression attribute
      expAtt = new ExpAttribute(ty, Code3aGenerator.genBinOp(Inst3a.TAC.DIV, temp, p1, p2), temp);
    }  
  | pe=primary_exp[symTab] 
    { expAtt = pe; }
  ;
 
primary_exp [SymbolTable symTab] returns [ExpAttribute expAtt]
  : INTEGER
    {
      // Create a const symbol 3ad code and just put the integer value
      ConstSymbol cs = new ConstSymbol(Integer.parseInt($INTEGER.text));
      expAtt = new ExpAttribute(Type.INT, new Code3a(), cs);
    }
  | IDENT
    {
      // Get the ident from the symtab
      Operand3a id = symTab.lookup($IDENT.text);
      // If the ident wasn't found
      if (id == null) Errors.unknownIdentifier($IDENT, $IDENT.text, null);

      // Return the ExpAttribute corresponding to it
      expAtt = new ExpAttribute(id.type, new Code3a(), id);
    }
  | ^(NEGAT pe=primary_exp[symTab])
    {

      // Get a new temporary var for the 3ad code
      VarSymbol temp = SymbDistrib.newTemp();

      // Return the ExpAttribute corresponding to it
      expAtt = new ExpAttribute(pe.type, Code3aGenerator.genBinOp(Inst3a.TAC.NEG, temp, pe, null), temp);
    }
  ;