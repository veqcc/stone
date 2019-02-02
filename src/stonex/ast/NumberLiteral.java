package stonex.ast;
import stonex.Token;

public class NumberLiteral extends ASTLeaf {
    public NumberLiteral(Token t) {
        super(t);
    }

    public int value() {
        return token().getNumber();
    }
}