package stonex;
import java.util.ArrayList;
import stonex.ast.ClassBody;
import stonex.ast.ClassStmnt;
import stonex.ast.DefStmnt;

public class ClassInfo {
    private ClassStmnt definition;
    private Environment environment;
    private ClassInfo superClass;
    private Symbols methods, fields;
    private DefStmnt[] methodDefs;

    public ClassInfo(ClassStmnt cs, Environment env, Symbols methods, Symbols fields) throws Exception {
        this.definition = cs;
        this.environment = env;
        this.methods = methods;
        this.fields = fields;
        this.methodDefs = null;

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

    public int size() {
        return fields.size();
    }

    public void copyTo(Symbols f, Symbols m, ArrayList<DefStmnt> mlist) {
        f.append(fields);
        m.append(methods);
        for (DefStmnt def: methodDefs) {
            mlist.add(def);
        }
    }

    public Integer fieldIndex(String name) {
        return fields.find(name);
    }

    public Integer methodIndex(String name) {
        return methods.find(name);
    }

    public Object method(StoneObject self, int index) {
        DefStmnt def = methodDefs[index];
        return new Method(def.parameters(), def.body(), environment(), def.locals(), self);
    }

    public void setMethods(ArrayList<DefStmnt> methods) {
        methodDefs = methods.toArray(new DefStmnt[methods.size()]);
    }
}