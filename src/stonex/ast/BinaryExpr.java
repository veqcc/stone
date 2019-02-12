package stonex.ast;
import stonex.Env;
import stonex.Environment;
import stonex.StoneObject;
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

    public Object eval(Environment env) throws Exception {
        String op = operator();
        Object right = right().eval(env);
        if ("=".equals(op)) {
            return computeAssign(env, right);
        } else {
            Object left = left().eval(env);
            return computeOp(left, op, right);
        }
    }

    private Object computeAssign(Environment env, Object rvalue) throws Exception {
        ASTree l = left();
        if (l instanceof PrimaryExpr) {
            PrimaryExpr p = (PrimaryExpr)l;
            if (p.hasPostfix(0) && p.postfix(0) instanceof Dot) {
                Object t = p.evalSubExpr(env, 1);
                if (t instanceof StoneObject) {
                    return setField((StoneObject)t, (Dot)p.postfix(0), rvalue);
                }
            } else if (p.hasPostfix(0) && p.postfix(0) instanceof ArrayRef) {
                Object t = p.evalSubExpr(env, 1);
                if (t instanceof Object[]) {
                    ArrayRef aref = (ArrayRef)p.postfix(0);
                    Object index = aref.index().eval(env);
                    if (index instanceof Integer) {
                        ((Object[])t)[(Integer)index] = rvalue;
                        return rvalue;
                    }
                }
                throw new Exception();
            }
        }

        if (l instanceof Name) {
            env.put(((Name)l).name(), rvalue);
            return rvalue;
        }

        throw new Exception();
    }

    private Object computeOp(Object left, String op, Object right) throws Exception {
        if (left instanceof Integer && right instanceof Integer) {
            return computeNumber((Integer)left, op, (Integer)right);
        } else {
            switch (op) {
                case "+": return String.valueOf(left) + String.valueOf(right);
                case "==":
                    if (left == null) {
                        return right == null ? 1 : 0;
                    } else {
                        return left.equals(right) ? 1 : 0;
                    }
                default: throw new Exception();
            }
        }
    }

    private Object computeNumber(Integer left, String op, Integer right) throws Exception {
        int l = left.intValue();
        int r = right.intValue();
        switch (op) {
            case "+": return l + r;
            case "-": return l - r;
            case "*": return l * r;
            case "/": return l / r;
            case "%": return l % r;
            case "==": return l == r ? 1 : 0;
            case ">": return l > r ? 1 : 0;
            case "<": return l < r ? 1 : 0;
            default: throw new Exception();
        }
    }

    private Object setField(StoneObject obj, Dot expr, Object rvalue) throws Exception {
        String name = expr.name();
        try {
            obj.write(name, rvalue);
            return rvalue;
        } catch (Exception e) {
            throw e;
        }
    }
}