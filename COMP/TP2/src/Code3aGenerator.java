import java.util.*;

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
	private Code3aGenerator() { }


	/**
	 * Generates the 3a statement: VAR t
	 */
	public static Code3a genVar(Operand3a t) {
		Inst3a i = new Inst3a(Inst3a.TAC.VAR, t, null, null);
		return new Code3a(i);
	}


	/**
	 * Generate code for a binary operation
	 *
	 * @param op
	 *			must be a code op: Inst3a.TAC.XXX
	 */
	public static Code3a genUnaryOp(Inst3a.TAC op, Operand3a temp, ExpAttribute exp1) {
		Code3a cod = exp1.code;
		cod.append(genVar(temp));
		cod.append(new Inst3a(op, temp, exp1.place, null));
		return cod;
	}


	/**
	 * Generate code for a binary operation
	 *
	 * @param op
	 *			must be a code op: Inst3a.TAC.XXX
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
	 */
	public static Code3a genAff(Operand3a var, ExpAttribute exp) {
		Code3a cod = new Code3a();
		cod.append(exp.code);
		cod.append(new Inst3a(Inst3a.TAC.COPY, var, exp.place, null));
		return cod;
	}


	/**
	 * Generate code for return
	 */
	public static Code3a genReturn(ExpAttribute exp) {
		Code3a code = new Code3a();

		// Do the return
		code.append(exp.code);
		code.append(new Inst3a(Inst3a.TAC.RETURN, exp.place, null, null));

		return code;
	}


	/**
	 * Generate code for IF ..... THEN
	 */
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
	 */
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
	 */
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
	 */
	public static Code3a genPrintString(String msg) {
		Code3a code = new Code3a();
		Data3a data = new Data3a(msg);
		code.appendData(data);
		code.append(new Inst3a(Inst3a.TAC.ARG, data.getLabel(), null, null));
		code.append(new Inst3a(Inst3a.TAC.CALL, null, SymbDistrib.builtinPrintS, null));
		return code;
	}


	/**
	 * Generate code for PrintInteger
	 */
	public static Code3a genPrintInteger(ExpAttribute exp) {
		Code3a code = new Code3a();
		code.append(exp.code);
		code.append(new Inst3a(Inst3a.TAC.ARG, exp.place, null, null));
		code.append(new Inst3a(Inst3a.TAC.CALL, null, SymbDistrib.builtinPrintN, null));
		return code;
	}


	/**
	 * Generate code for Read Integer
	 */
	public static Code3a genReadInteger(VarSymbol var) {
		Code3a code = new Code3a();
		code.append(new Inst3a(Inst3a.TAC.CALL, var, SymbDistrib.builtinRead, null));
		return code;
	}


	/**
	 * Generate code for a variable declaration
	 */
	public static Code3a genVarDeclaration(VarSymbol var) {
		Code3a code = new Code3a();
		code.append(new Inst3a(Inst3a.TAC.VAR, (Operand3a)var, null, null));
		return code;
	}


	/**
	 * Generate code for a function declaration
	 */
	public static Code3a genFunction(FunctionSymbol functionSymbol, Code3a paramsCode, Code3a statementCode) {
		Code3a code = new Code3a();

		// Generate the label (label [functionName])
		code.append(new Inst3a(Inst3a.TAC.LABEL, functionSymbol, null, null));

		// Generate the beginfunc instruction (beginfunc)
		code.append(new Inst3a(Inst3a.TAC.BEGINFUNC, null, null, null));

		// Put the params codes then
		code.append(paramsCode);

		// Put the statement codes then
		code.append(statementCode);

		// And in the end, the end of funtion code
		code.append(new Inst3a(Inst3a.TAC.ENDFUNC, null, null, null));

		// And return the code generated
		return code;
	}


	/**
	 * Generate code for everytime we have to append two codes
	 */
	public static Code3a concatenateCodes(Code3a c1, Code3a c2) {
		Code3a code = new Code3a();
		code.append(c1);
		code.append(c2);  // Null verification already done into append()
		return code;
	}


	/**
	 * Generate code for Instruction
	 */
	public static Code3a genInstruction(Code3a c) {
		Code3a code = new Code3a();
		code.append(c);
		return code;
	}


	/**
	 * Generate code for a function call
	 */
	public static ExpAttribute genFunctionCall(String funcName, FunctionType f, Code3a c) {

		// Generate a new place
		VarSymbol resultFunction = SymbDistrib.newTemp();

		// Create the ExpAttribute
		ExpAttribute exp = new ExpAttribute(f.getReturnType(), c, resultFunction);

		// Then call the function
		exp.code.append(new Inst3a(Inst3a.TAC.CALL, resultFunction, new LabelSymbol(funcName), null));

		// In the end, return this ExpAttribute
		return exp;
	}


	/**
	 * Generate code for a procedure call
	 */
	public static Code3a genProcedureCall(String funcName, Code3a c) {

		// Then call the function
		Code3a code = new Code3a();
		code.append(c);  // First, the arguments passed
		code.append(new Inst3a(Inst3a.TAC.CALL, null, new LabelSymbol(funcName), null));

		// In the end, return this code
		return code;
	}


	/**
	 * Generate code for a argument call
	 */
	public static Code3a genArg(ExpAttribute e) {

		Code3a code = e.code;
       	code.append(new Inst3a(Inst3a.TAC.ARG, e.place, null, null));
 		return code;
	}

	/**
	 * Generates the 3a statement: ARRAY
	 */
	public static Code3a genArrayElem(Operand3a t , ExpAttribute e) {
		Code3a code = new Code3a();
		code.append(new Inst3a(Inst3a.TAC.VARTAB, t,e.place , null));
		return code;
	}

	/**
	 * Generates the 3a statement: Affectation of ARRAY
	 */
	public static Code3a genAffarray(Code3a c, ExpAttribute e) {
		Code3a code = new Code3a();
		List<Inst3a> l = c.getCode();
		Inst3a i = l.get(0);
		code.append(new Inst3a(i.getOp(), i.getA(), i.getB(), e.place));
		return code;
	}

} // Code3aGenerator ***
