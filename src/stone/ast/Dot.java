package stone.ast;

import stone.*;
import java.util.List;

public class Dot extends Postfix {
    public Dot(List<ASTree> c) {
        super(c);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().getText();
    }

    public String toString() {
        return "." + name();
    }

    protected void initObject(ClassInfo ci, Environment env) {
        if (ci.superClass() != null) {
            initObject(ci.superClass(), env);
        }
        ci.body().eval(env);
    }

    protected void initObject(OptClassInfo ci, OptStoneObject obj, Environment env) {
        if (ci.superClass() != null) {
            initObject(ci.superClass(), obj, env);
        }
        ci.body().eval(env);
    }

    protected OptClassInfo classInfo = null;
    protected boolean isField;
    protected int index;

    public Object eval(Environment env, Object value) {
        String member = name();
        if (value instanceof OptClassInfo) {
            if ("new".equals(member)) {
                OptClassInfo ci = (OptClassInfo) value;
                ArrayEnv newEnv = new ArrayEnv(1, ci.environment());
                OptStoneObject so = new OptStoneObject(ci, ci.size());
                newEnv.put(0, 0, so);
                initObject(ci, so, newEnv);
                return so;
            }
        } else if (value instanceof OptStoneObject) {
            OptStoneObject target = (OptStoneObject) value;
            if (target.classInfo() != classInfo)
                updateCache(target);
            if (isField)
                return target.read(index);
            else
                return target.method(index);
        }
        throw new StoneException("bad member access: " + member, this);
    }

    protected void updateCache(OptStoneObject target) {
        String member = name();
        classInfo = target.classInfo();
        Integer i = classInfo.fieldIndex(member);
        if (i != null) {
            isField = true;
            index = i;
            return;
        }
        i = classInfo.methodIndex(member);
        if (i != null) {
            isField = false;
            index = i;
            return;
        }
        throw new StoneException("bad member access: " + member, this);
    }
}
