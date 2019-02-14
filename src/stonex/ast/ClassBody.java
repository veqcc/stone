package stonex.ast;
import stonex.Environment;
import stonex.Symbols;
import java.util.ArrayList;
import java.util.List;

public class ClassBody extends ASTList {
    public ClassBody(List<ASTree> c) {
        super(c);
    }

    public Object eval(Environment env) throws Exception {
        for (ASTree t: this) {
            if (!(t instanceof DefStmnt)) {
                t.eval(env);
            }
        }
        return null;
    }

    public void lookup(Symbols syms, Symbols methodNames, Symbols fieldNames, ArrayList<DefStmnt> methods) throws Exception {
        for (ASTree t: this) {
            if (t instanceof DefStmnt) {
                DefStmnt def = (DefStmnt)t;
                int oldSize = methodNames.size();
                int i = methodNames.putNew(def.name());
                if (i >= oldSize) {
                    methods.add(def);
                } else {
                    methods.set(i, def);
                }
                def.lookupAsMethod(fieldNames);
            } else {
                t.lookup(syms);
            }
        }
    }
}