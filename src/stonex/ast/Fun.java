package stonex.ast;
import com.sun.javafx.tools.packager.Param;
import stonex.Environment;
import stonex.Function;
import stonex.Symbols;

import java.util.List;

public class Fun extends ASTList {
    private int size = -1;

    public Fun(List<ASTree> c) {
        super(c);
    }

    public ParameterList parameters() {
        return (ParameterList)child(0);
    }

    public BlockStmnt body() {
        return (BlockStmnt)child(1);
    }

    public String toString() {
        return "(fun " + parameters() + " " + body() + ")";
    }

    public void lookup(Symbols syms) throws Exception {
        size = lookup(syms, parameters(), body());
    }

    public Object eval(Environment env) {
        return new Function(parameters(), body(), env, size);
    }

    public static int lookup(Symbols syms, ParameterList params, BlockStmnt body) throws Exception {
        Symbols newSyms = new Symbols(syms);
        params.lookup(newSyms);
        body.lookup(newSyms);
        return newSyms.size();
    }
}