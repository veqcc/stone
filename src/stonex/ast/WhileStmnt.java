package stonex.ast;
import stonex.Environment;
import java.util.List;

public class WhileStmnt extends ASTList {
    public WhileStmnt(List<ASTree> c) {
        super(c);
    }

    public ASTree condition() {
        return child(0);
    }

    public ASTree body() {
        return child(1);
    }

    public String toString() {
        return "(while " + condition() + " " + body() + ")";
    }

    public Object eval(Environment env) throws Exception {
        Object result = 0;
        for (;;) {
            Object c = condition().eval(env);
            if (c instanceof Integer && (Integer)c == 0) {
                return result;
            } else {
                result = body().eval(env);
            }
        }
    }
}