package stonex.ast;
import stonex.Environment;
import stonex.Token;

public class Name extends ASTLeaf {
    public Name(Token t) {
        super(t);
    }

    public String name() {
        return token().getText();
    }

    public Object eval(Environment env) throws Exception {
        Object value = env.get(name());
        if (value == null) {
            throw new Exception();
        } else {
            return value;
        }
    }
}