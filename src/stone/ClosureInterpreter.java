package stone;

import stone.ast.ASTree;
import stone.ast.NullStmnt;

public class ClosureInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClosureParser(), new NestedEnv());
    }

    public static void run(ClosureParser cp, NestedEnv env) throws ParseException {
        Lexer lexer = new Lexer(new CodeDialog());
        while (lexer.peek(0) != Token.EOF) {
            ASTree t = cp.parse(lexer);
            if (!(t instanceof NullStmnt)) {
                Object r = t.eval(env);
                System.out.println("=> " + r);
            }
        }
    }
}