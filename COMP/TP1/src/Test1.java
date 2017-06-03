import org.antlr.runtime.*;

public class Test1 {
    public static void main(String[] args) throws Exception {
        ANTLRInputStream input = new ANTLRInputStream(System.in);
        Partie1Lexer lexer = new Partie1Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Partie1Parser parser = new Partie1Parser(tokens);
        parser.doc();
    }
}
