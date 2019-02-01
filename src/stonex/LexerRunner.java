package stonex;

import java.io.Reader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JFileChooser;

public class LexerRunner {
    public static void main(String[] args) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            Lexer l = new Lexer(br);
            for (Token t; (t = l.read()) != Token.EOF; ) {
                System.out.println("=> " + t.getText());
            }
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}