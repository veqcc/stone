package stonex.ast;
import stonex.*;
import java.util.ArrayList;
import java.util.List;

public class ClassStmnt extends ASTList {
    public ClassStmnt(List<ASTree> c) {
        super(c);
    }

    public String name() {
        return ((ASTLeaf)child(0)).token().getText();
    }

    public String superClass() {
        if (numChildren() < 3) {
            return null;
        } else {
            return ((ASTLeaf)child(1)).token().getText();
        }
    }

    public ClassBody body() {
        return (ClassBody)child(numChildren() - 1);
    }

    public String toString() {
        String parent = superClass();
        if (parent == null) {
            return "*";
        }
        return "(class " + name() + " " + parent + " " + body() + ")";
    }

    public void lookup(Symbols syms) {}

    public Object eval(Environment env) throws Exception {
        Symbols methodNames = new MemberSymbols(env.symbols(), MemberSymbols.METHOD);
        Symbols fieldNames = new MemberSymbols(methodNames, MemberSymbols.FIELD);
        ClassInfo ci = new ClassInfo(this, env, methodNames, fieldNames);
        env.put(name(), ci);
        ArrayList<DefStmnt> methods = new ArrayList<DefStmnt>();
        if (ci.superClass() != null) {
            ci.superClass().copyTo(fieldNames, methodNames, methods);
        }
        Symbols newSyms = new SymbolThis(fieldNames);
        body().lookup(newSyms, methodNames, fieldNames, methods);
        ci.setMethods(methods);
        return name();
    }
}