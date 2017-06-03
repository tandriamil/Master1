import org.antlr.runtime.*;

public class Test2 {
    public static void main(String[] args) throws Exception {
        ANTLRInputStream input = new ANTLRInputStream(System.in);
        Partie2Lexer lexer = new Partie2Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Partie2Parser parser = new Partie2Parser(tokens);
        parser.doc();
    }
}
