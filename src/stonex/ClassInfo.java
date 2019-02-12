package stonex;
import stonex.ast.ClassBody;
import stonex.ast.ClassStmnt;

public class ClassInfo {
    private ClassStmnt definition;
    private Environment environment;
    private ClassInfo superClass;

    public ClassInfo(ClassStmnt cs, Environment env) throws Exception {
        definition = cs;
        environment = env;
        Object obj = env.get(cs.superClass());
        if (obj == null) {
            superClass = null;
        } else if (obj instanceof ClassInfo) {
            superClass = (ClassInfo)obj;
        } else {
            throw new Exception();
        }
    }

    public String name() {
        return definition.name();
    }

    public ClassInfo superClass() {
        return superClass;
    }

    public ClassBody body() {
        return definition.body();
    }

    public Environment environment() {
        return environment;
    }

    public String toString() {
        return "<class " + name() + ">";
    }
}