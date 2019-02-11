package stonex.ast;
import stonex.Environment;
import java.util.List;

public class NegativeExpr extends ASTList {
    public NegativeExpr(List<ASTree> c) {
        super(c);
    }

    public ASTree operand() {
        return child(0);
    }

    public String toString() {
        return "-" + operand();
    }

    public Object eval(Environment env) throws Exception {
        Object v = operand().eval(env);
        if (v instanceof Integer) {
            return - (Integer) v;
        } else {
            throw new Exception();
        }
    }
}