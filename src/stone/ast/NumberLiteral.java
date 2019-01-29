package stone.ast;

import stone.Environment;
import stone.Token;

public class NumberLiteral extends ASTLeaf {
    public NumberLiteral(Token t) {
        super(t);
    }

    public int value() {
        return token().getNumber();
    }

    public Object eval(Environment e) {
        return value();
    }
}
