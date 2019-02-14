package stonex;
import stonex.ast.BlockStmnt;
import stonex.ast.ParameterList;

public class Function {
    private ParameterList parameters;
    private BlockStmnt body;
    protected Environment env;
    protected int size;

    public Function(ParameterList parameters, BlockStmnt body, Environment env, int size) {
        this.parameters = parameters;
        this.body = body;
        this.env = env;
        this.size = size;
    }

    public ParameterList parameters() {
        return parameters;
    }

    public BlockStmnt body() {
        return body;
    }

    public Environment makeEnv() throws Exception {
        return new Env(size, env);
    }

    public String toString() {
        return "<fun: " + hashCode() + ">";
    }
}