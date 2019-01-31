package stone.ast;

import stone.Environment;
import stone.StoneException;
import stone.StoneObject;
import stone.Symbols;
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

    protected Object setField(StoneObject obj, Dot expr, Object rvalue) {
        String name = expr.name();
        try {
            obj.write(name, rvalue);
            return rvalue;
        } catch (StoneObject.AccessException e) {
            throw new StoneException("bad member access " + location() + ": " + name);
        }
    }

    public void lookup(Symbols syms) {
        ASTree left = left();
        if ("=".equals(operator())) {
            if (left instanceof Name) {
                ((Name)left).lookupForAssign(syms);
                right().lookup(syms);
                return;
            }
        }
        left.lookup(syms);
        right().lookup(syms);
    }

    protected Object computeAssign(Environment env, Object rvalue) {
        ASTree le = left();
        if (le instanceof Name) {
            ((Name)le).evalForAssign(env, rvalue);
            return rvalue;
        } else if (le instanceof PrimaryExpr) {
            PrimaryExpr p = (PrimaryExpr) le;
            Object t = p.evalSubExpr(env, 1);

            if (p.hasPostfix(0) && p.postfix(0) instanceof Dot) {
                if (t instanceof StoneObject) {
                    return setField((StoneObject) t, (Dot) p.postfix(0), rvalue);
                }
            } else if (p.hasPostfix(0) && p.postfix(0) instanceof ArrayRef) {
                if (t instanceof Object[]) {
                    ArrayRef aref = (ArrayRef) p.postfix(0);
                    Object index = aref.index().eval(env);
                    if (index instanceof Integer) {
                        ((Object[]) t)[(Integer) index] = rvalue;
                        return rvalue;
                    }
                }
                throw new StoneException("bad array access", this);
            }
        }
        throw new StoneException("bad assignment", this);
    }
}
