package stone.ast;

import stone.Environment;
import stone.StoneException;
import java.util.List;

public class BinaryExpr extends ASTList {
    public BinaryExpr(List<ASTree> c) {
        super(c);
    }

    public ASTree left() {
        return child(0);
    }

    public String operator() {
        return ((ASTLeaf) child(1)).token().getText();
    }

    public ASTree right() {
        return child(2);
    }

    public Object eval(Environment env) {
        String op = operator();
        if ("=".equals(op)) {
            Object right = right().eval(env);
            return computeAssign(env, right);
        } else {
            Object left = left().eval(env);
            Object right = right().eval(env);
            return computeOp(left, op, right);
        }
    }

    protected Object computeAssign(Environment env, Object rvalue) {
        ASTree l = left();
        if (l instanceof Name) {
            env.put(((Name) l).name(), rvalue);
            return rvalue;
        } else
            throw new StoneException("bad assignment", this);
    }

    protected Object computeOp(Object left, String op, Object right) {
        if (left instanceof Integer && right instanceof Integer) {
            return computeNumber((Integer) left, op, (Integer) right);
        } else if (op.equals("+"))
            return String.valueOf(left) + String.valueOf(right);
        else if (op.equals("==")) {
            if (left == null)
                return right == null ? 1 : 0;
            else
                return left.equals(right) ? 1 : 0;
        } else
            throw new StoneException("bad type", this);
    }

    protected Object computeNumber(Integer left, String op, Integer right) {
        int a = left.intValue();
        int b = right.intValue();
        if (op.equals("+"))
            return a + b;
        else if (op.equals("-"))
            return a - b;
        else if (op.equals("*"))
            return a * b;
        else if (op.equals("/"))
            return a / b;
        else if (op.equals("%"))
            return a % b;
        else if (op.equals("=="))
            return a == b ? 1 : 0;
        else if (op.equals(">"))
            return a > b ? 1 : 0;
        else if (op.equals("<"))
            return a < b ? 1 : 0;
        else
            throw new StoneException("bad operator", this);
    }
}
