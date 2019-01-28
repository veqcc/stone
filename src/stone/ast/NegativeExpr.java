package stone.ast;
import stone.StoneException;

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

    public Object eval(Environment env) {
        Object v = operand().eval(env);
        if (v instanceof Integer)
            return new Integer(-((Integer) v).intValue());
        else
            throw new StoneException("bad type for -", this);
    }
}
