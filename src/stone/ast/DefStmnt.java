package stone.ast;

import stone.Environment;
import stone.Symbols;
import stone.OptFunction;
import stone.SymbolThis;
import java.util.List;

public class DefStmnt extends ASTList {
    public DefStmnt(List<ASTree> c) {
        super(c);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().getText();
    }

    public ParameterList parameters() {
        return (ParameterList) child(1);
    }

    public BlockStmnt body() {
        return (BlockStmnt) child(2);
    }

    public String toString() {
        return "(def " + name() + " " + parameters() + " " + body() + ")";
    }

    protected int index, size;

    public void lookup(Symbols syms) {
        index = syms.putNew(name());
        size = Fun.lookup(syms, parameters(), body());
    }

    public Object eval(Environment env) {
        env.put(0, index, new OptFunction(parameters(), body(), env, size));
        return name();
    }

    public int locals() {
        return size;
    }

    public void lookupAsMethod(Symbols syms) {
        Symbols newSyms = new Symbols(syms);
        newSyms.putNew(SymbolThis.NAME);
        parameters().lookup(newSyms);
        body().lookup(newSyms);
        size = newSyms.size();
    }
}
