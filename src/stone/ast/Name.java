package stone.ast;

import stone.Environment;
import stone.StoneException;
import stone.Token;
import stone.Symbols;
import stone.Symbols.Location;

public class Name extends ASTLeaf {
    protected static final int UNKNOWN = -1;
    protected int nest, index;

    public Name(Token t) {
        super(t);
        index = UNKNOWN;
    }

    public String name() {
        return token().getText();
    }

    public void lookup(Symbols syms) {
        Location loc = syms.get(name());
        if (loc == null)
            throw new StoneException("undefined name: " + name(), this);
        else {
            nest = loc.nest;
            index = loc.index;
        }
    }

    public void lookupForAssign(Symbols syms) {
        Location loc = syms.put(name());
        nest = loc.nest;
        index = loc.index;
    }

    public Object eval(Environment env) {
        if (index == UNKNOWN) {
            return env.get(name());
        } else {
            return env.get(nest, index);
        }
    }

    public void evalForAssign(Environment env, Object value) {
        if (index == UNKNOWN) {
            env.put(name(), value);
        } else {
            env.put(nest, index, value);
        }
    }
}
