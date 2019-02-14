package stonex.ast;
import stonex.Function;
import stonex.Environment;
import stonex.SymbolThis;
import stonex.Symbols;
import java.util.List;

public class DefStmnt extends ASTList {
    private int index, size;

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

    public int locals() {
        return size;
    }

    public void lookup(Symbols syms) throws Exception {
        index = syms.putNew(name());
        size = Fun.lookup(syms, parameters(), body());
    }

    public Object eval(Environment env) throws Exception {
        env.put(0, index, new Function(parameters(), body(), env, size));
        return name();
    }

    public void lookupAsMethod(Symbols syms) throws Exception {
        Symbols newSyms = new Symbols(syms);
        newSyms.putNew(SymbolThis.NAME);
        parameters().lookup(newSyms);
        body().lookup(newSyms);
        size = newSyms.size();
    }
}