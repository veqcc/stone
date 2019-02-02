package stonex;
import stonex.ast.ASTree;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;

public class ParserRunner {
    public static void main(String[] args) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            Lexer l = new Lexer(br);
            BasicParser bp = new BasicParser();
            while (l.peek(0) != Token.EOF) {
                ASTree ast = bp.parse(l);
                System.out.println("=> " + ast.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}