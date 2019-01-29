package stone.ast;

import stone.Environment;
import stone.Function;
import java.util.List;

public class Fun extends ASTList {
    public Fun(List<ASTree> c) {
        super(c);
    }

    public ParameterList parameters() {
        return (ParameterList) child(0);
    }

    public BlockStmnt body() {
        return (BlockStmnt) child(1);
    }

    public String toString() {
        return "(fun " + parameters() + " " + body() + ")";
    }

    public Object eval(Environment env) {
        return new Function(parameters(), body(), env);
    }
}
