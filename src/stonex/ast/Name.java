package stonex.ast;
import stonex.*;

public class Name extends ASTLeaf {
    private static final int UNKNOWN = -1;
    private int nest, index;

    public Name(Token t) {
        super(t);
        index = UNKNOWN;
    }

    public String name() {
        return token().getText();
    }

    public void lookup(Symbols syms) throws Exception {
        Symbols.Location loc = syms.get(name());
        if (loc == null) {
            throw new Exception();
        } else {
            nest = loc.nest;
            index = loc.index;
        }
    }

    public void lookupForAssign(Symbols syms) {
        Symbols.Location loc = syms.put(name());
        nest = loc.nest;
        index = loc.index;
    }

    public Object eval(Environment env) throws Exception {
        if (index == UNKNOWN) {
            return env.get(name());
        } else if (nest == MemberSymbols.FIELD) {
            return getThis(env).read(index);
        } else if (nest == MemberSymbols.METHOD) {
            return getThis(env).method(index);
        } else {
            return env.get(nest, index);
        }
    }

    public void evalForAssign(Environment env, Object value) throws Exception {
        if (index == UNKNOWN) {
            env.put(name(), value);
        } else if (nest == MemberSymbols.FIELD) {
            getThis(env).write(index, value);
        } else if (nest == MemberSymbols.METHOD) {
            throw new Exception();
        } else {
            env.put(nest, index, value);
        }
    }

    private StoneObject getThis(Environment env) {
        return (StoneObject)env.get(0, 0);
    }
}