package stonex.ast;
import stonex.Environment;
import stonex.Symbols;
import java.util.List;

public class ParameterList extends ASTList {
    private int[] offsets = null;

    public ParameterList(List<ASTree> c) {
        super(c);
    }

    public String name(int i) {
        return ((ASTLeaf)child(i)).token().getText();
    }

    public int size() {
        return numChildren();
    }

    public void lookup(Symbols syms) throws Exception {
        int s = size();
        offsets = new int[s];
        for (int i = 0; i < s; i++) {
            offsets[i] = syms.putNew(name(i));
        }
    }

    public void eval(Environment env, int index, Object value) throws Exception {
        env.put(0, offsets[index], value);
    }
}