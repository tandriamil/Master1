/**
 * This class implements all the methods for 3a code generation (NOTE: this
 * class must be coded by the student; the methods indicated here can be seen as
 * a suggestion, but are not actually necessary).
 * 
 * @author MLB
 * 
 */
public class Code3aGenerator {

	// Constructor not needed
	private Code3aGenerator() {
	}

	/**
	 * Generates the 3a statement: VAR t
	 **/
	public static Code3a genVar(Operand3a t) {
		Inst3a i = new Inst3a(Inst3a.TAC.VAR, t, null, null);
		return new Code3a(i);
	}

	/**
	 * Generate code for a binary operation
	 * 
	 * @param op
	 *            must be a code op: Inst3a.TAC.XXX
	 */
	public static Code3a genBinOp(Inst3a.TAC op, Operand3a temp, ExpAttribute exp1, ExpAttribute exp2) {
		Code3a cod = exp1.code;
		cod.append(exp2.code);
		cod.append(genVar(temp));
		cod.append(new Inst3a(op, temp, exp1.place, exp2.place));
		return cod;
	}

	/** 
	* Generate code for affectation
	**/

	public static Code3a genAff(Operand3a var, ExpAttribute exp) {
		Code3a cod = exp.code;
		cod.append(new Inst3a(Inst3a.TAC.COPY, var, exp.place, null));
		return cod;
	}
	
	/** 
	* Generate code for IF ..... THEN
	**/

	public static Code3a genIF(ExpAttribute exp, Code3a code1) {
		LabelSymbol end = SymbDistrib.newLabel();
		Code3a code = new Code3a();
		code.append(new Inst3a(Inst3a.TAC.IFZ, exp.place, end, null));
		code.append(code1);
        code.append(new Inst3a(Inst3a.TAC.LABEL, end, null, null));
		return code;
	}

	/** 
	* Generate code for IF ..... THEN ...... ELSE
	**/

	public static Code3a genIFELSE(ExpAttribute exp, Code3a code1, Code3a code2) {
		LabelSymbol elSe = SymbDistrib.newLabel();
		LabelSymbol end = SymbDistrib.newLabel();
		Code3a code = new Code3a();
		code.append(new Inst3a(Inst3a.TAC.IFZ, exp.place, elSe, null));
		code.append(code1);
        code.append(new Inst3a(Inst3a.TAC.GOTO, end, null, null));
        code.append(new Inst3a(Inst3a.TAC.LABEL, elSe, null, null));
        code.append(code2);
        code.append(new Inst3a(Inst3a.TAC.LABEL, end, null, null));
		return code;
	}

	/** 
	* Generate code for WHILE ..... DO ...... DONE
	**/

	public static Code3a genWHILE(ExpAttribute exp, Code3a code1) {
		LabelSymbol repeat = SymbDistrib.newLabel();
		LabelSymbol end = SymbDistrib.newLabel();
		Code3a code = new Code3a();
		code.append(new Inst3a(Inst3a.TAC.LABEL, repeat, null, null));
		code.append(new Inst3a(Inst3a.TAC.IFZ, exp.place, end, null));
		code.append(code1);
        code.append(new Inst3a(Inst3a.TAC.GOTO, repeat, null, null));
        code.append(new Inst3a(Inst3a.TAC.LABEL, end, null, null));
		return code;
	}

	/** 
	* Generate code for PrintSting
	**/

	public static Code3a genPrintString(String msg) {
		Code3a code = new Code3a();
		Data3a data = new Data3a(msg)
		code.append(new Inst3a(Inst3a.TAC.LABEL, data.getLabel, null, null));
		code.append(new Inst3a(Inst3a.TAC.CALL, null, SymbDistrib.builtinPrintS, null));
		return code;
	}

	/** 
	* Generate code for PrintInteger
	**/

	public static Code3a genPrintInteger(ExpAttribute exp) {
		Code3a code = new Code3a();
		code.append(new Inst3a(Inst3a.TAC.ARG, exp.place, null, null));
		code.append(new Inst3a(Inst3a.TAC.CALL, null, SymbDistrib.builtinPrintN, null));
		return code;
	}

	/** 
	* Generate code for Read Integer
	**/

	public static Code3a genReadInteger(VarSymbol var) {
		Code3a code = new Code3a();
		code.append(new Inst3a(Inst3a.TAC.CALL, var, SymbDistrib.builtinRead, null));
		return code;
	}

} // Code3aGenerator ***
