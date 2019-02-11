package stonex.ast;
import stonex.Environment;
import stonex.Token;

public class StringLiteral extends ASTLeaf {
    public StringLiteral(Token t) {
        super(t);
    }

    public String value() {
        return token().getText();
    }

    public Object eval(Environment env) throws Exception {
        return value();
    }
}