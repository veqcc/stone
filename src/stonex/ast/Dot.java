package stonex.ast;
import stonex.Env;
import stonex.ClassInfo;
import stonex.Environment;
import stonex.StoneObject;

import java.util.List;

public class Dot extends Postfix {
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
                Env e = new Env(ci.environment());
                StoneObject so = new StoneObject(e);
                e.putNew("this", so);
                initObject(ci, e);
                return so;
            }
        } else if (value instanceof StoneObject) {
            try {
                return ((StoneObject)value).read(member);
            } catch (Exception e) {
                throw new Exception();
            }
        }
        throw new Exception();
    }

    private void initObject(ClassInfo ci, Environment env) throws Exception {
        if (ci.superClass() != null) {
            initObject(ci.superClass(), env);
        }
        ci.body().eval(env);
    }
}