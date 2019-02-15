package stonex.ast;
import stonex.Env;
import stonex.ClassInfo;
import stonex.Environment;
import stonex.StoneObject;

import java.util.List;

public class Dot extends Postfix {
    private ClassInfo classInfo = null;
    private boolean isField;
    private int index;

    public Dot(List<ASTree> c) {
        super(c);
    }

    public String name() {
        return ((ASTLeaf)child(0)).token().getText();
    }

    public String toString() {
        return "." + name();
    }

    public Object eval(Environment env, Object value) throws Exception {
        String member = name();
        if (value instanceof ClassInfo) {
            if ("new".equals(member)) {
                ClassInfo ci = (ClassInfo)value;
                Env e = new Env(1, ci.environment());
                StoneObject so = new StoneObject(ci, ci.size());
                e.put(0, 0, so);
                initObject(ci, so, e);
                return so;
            }
        }

        if (value instanceof StoneObject) {
            StoneObject target = (StoneObject)value;
            if (target.classInfo() != classInfo) {
                updateCache(target);
            }
            if (isField) {
                return target.read(index);
            } else {
                return target.method(index);
            }
        }

        throw new Exception();
    }

    private void updateCache(StoneObject target) throws Exception {
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

        throw new Exception();
    }

    private void initObject(ClassInfo ci, StoneObject obj, Environment env) throws Exception {
        if (ci.superClass() != null) {
            initObject(ci.superClass(), obj, env);
        }
        ci.body().eval(env);
    }
}