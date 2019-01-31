package stone.ast;

import stone.Environment;
import stone.StoneException;
import stone.Token;
import stone.Symbols;
import stone.Symbols.Location;
import stone.MemberSymbols;
import stone.OptStoneObject;

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
        if (index == UNKNOWN)
            return env.get(name());
        else if (nest == MemberSymbols.FIELD)
            return getThis(env).read(index);
        else if (nest == MemberSymbols.METHOD)
            return getThis(env).method(index);
        else
            return env.get(nest, index);
    }

    public void evalForAssign(Environment env, Object value) {
        if (index == UNKNOWN)
            env.put(name(), value);
        else if (nest == MemberSymbols.FIELD)
            getThis(env).write(index, value);
        else if (nest == MemberSymbols.METHOD)
            throw new StoneException("cannot update a method: " + name(),
                    this);
        else
            env.put(nest, index, value);
    }

    protected OptStoneObject getThis(Environment env) {
        return (OptStoneObject) env.get(0, 0);
    }
}
