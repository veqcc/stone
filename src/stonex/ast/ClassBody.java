package stonex.ast;
import stonex.Environment;

import java.util.List;

public class ClassBody extends ASTList {
    public ClassBody(List<ASTree> c) {
        super(c);
    }

    public Object eval(Environment env) throws Exception {
        for (ASTree t: this) {
            t.eval(env);
        }
        return null;
    }
}