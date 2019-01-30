package stone;

import stone.ast.ASTree;
import stone.ast.NullStmnt;

public class ArrayInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ArrayParser(), new Natives().environment(new NestedEnv()));
    }

    public static void run(ArrayParser ap, Environment env) throws ParseException {
        Lexer lexer = new Lexer(new CodeDialog());
        while (lexer.peek(0) != Token.EOF) {
            ASTree t = ap.parse(lexer);
            if (!(t instanceof NullStmnt)) {
                Object r = t.eval(env);
                System.out.println("=> " + r);
            }
        }
    }
}