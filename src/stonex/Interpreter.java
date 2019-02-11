package stonex;
import stonex.ast.ASTree;
import stonex.ast.NullStmnt;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Interpreter {
    public static void main(String[] args) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            Lexer l = new Lexer(br);
            BasicParser bp = new BasicParser();
            Environment env = new Natives().environment(new Env());
            while (l.peek(0) != Token.EOF) {
                ASTree t = bp.parse(l);
                if (!(t instanceof NullStmnt)) {
                    Object r = t.eval(env);
                    System.out.println("=> " + r);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}