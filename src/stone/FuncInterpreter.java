package stone;

import stone.ast.ASTree;
import stone.ast.NullStmnt;

public class FuncInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new FuncParser(), new NestedEnv());
    }

    public static void run(FuncParser fp, NestedEnv env) throws ParseException {
        Lexer lexer = new Lexer(new CodeDialog());
        while (lexer.peek(0) != Token.EOF) {
            ASTree t = fp.parse(lexer);
            if (!(t instanceof NullStmnt)) {
                Object r = t.eval(env);
                System.out.println("=> " + r);
            }
        }
    }
}