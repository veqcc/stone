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
    public Object eval(Environment env, Object value) {
        String member = name();
        if (value instanceof OptClassInfo) {
            if ("new".equals(member)) {
                OptClassInfo ci = (OptClassInfo)value;
                ArrayEnv newEnv = new ArrayEnv(1, ci.environment());
                OptStoneObject so = new OptStoneObject(ci, ci.size());
                newEnv.put(0, 0, so);
                initObject(ci, so, newEnv);
                return so;
            }
        }
        else if (value instanceof OptStoneObject) {
            try {
                return ((OptStoneObject)value).read(member);
            } catch (OptStoneObject.AccessException e) {}
        }
        throw new StoneException("bad member access: " + member, this);
    }
    protected void initObject(OptClassInfo ci, OptStoneObject obj, Environment env) {
        if (ci.superClass() != null) {
            initObject(ci.superClass(), obj, env);
        }
        ci.body().eval(env);
    }
}
