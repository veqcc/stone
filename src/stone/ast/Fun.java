package stone.ast;

import stone.Symbols;
import stone.Environment;
import stone.OptFunction;
import java.util.List;

public class Fun extends ASTList {
    public Fun(List<ASTree> c) {
        super(c);
    }

    public ParameterList parameters() {
        return (ParameterList) child(0);
    }

    public BlockStmnt body() {
        return (BlockStmnt) child(1);
    }

    public String toString() {
        return "(fun " + parameters() + " " + body() + ")";
    }

    protected int size = -1;

    public void lookup(Symbols syms) {
        size = lookup(syms, parameters(), body());
    }

    public Object eval(Environment env) {
        return new OptFunction(parameters(), body(), env, size);
    }

    public static int lookup(Symbols syms, ParameterList params, BlockStmnt body) {
        Symbols newSyms = new Symbols(syms);
        params.lookup(newSyms);
        body.lookup(newSyms);
        return newSyms.size();
    }
}
