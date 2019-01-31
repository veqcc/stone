package stone.ast;

import stone.Symbols;
import stone.Environment;
import java.util.List;

public class ParameterList extends ASTList {
    public ParameterList(List<ASTree> c) {
        super(c);
    }

    public String name(int i) {
        return ((ASTLeaf) child(i)).token().getText();
    }

    public int size() {
        return numChildren();
    }

    protected int[] offsets = null;

    public void lookup(Symbols syms) {
        int s = size();
        offsets = new int[s];
        for (int i = 0; i < s; i++)
            offsets[i] = syms.putNew(name(i));
    }

    public void eval(Environment env, int index, Object value) {
        env.put(0, offsets[index], value);
    }
}
