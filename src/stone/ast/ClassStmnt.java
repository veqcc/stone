package stone.ast;

import stone.Environment;
import stone.ClassInfo;
import java.util.List;

public class ClassStmnt extends ASTList {
    public ClassStmnt(List<ASTree> c) {
        super(c);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().getText();
    }

    public String superClass() {
        if (numChildren() < 3) {
            return null;
        } else {
            return ((ASTLeaf) child(1)).token().getText();
        }
    }

    public ClassBody body() {
        return (ClassBody) child(numChildren() - 1);
    }

    public String toString() {
        String parent = superClass();
        if (parent == null) {
            parent = "*";
        }
        return "(class " + name() + " " + parent + " " + body() + ")";
    }

    public Object eval(Environment env) {
        ClassInfo ci = new ClassInfo(this, env);
        env.put(name(), ci);
        return name();
    }
}
