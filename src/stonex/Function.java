package stonex;
import stonex.ast.BlockStmnt;
import stonex.ast.ParameterList;

public class Function {
    private ParameterList parameters;
    private BlockStmnt body;
    private Environment env;

    public Function(ParameterList parameters, BlockStmnt body, Environment env) {
        this.parameters = parameters;
        this.body = body;
        this.env = env;
    }

    public ParameterList parameters() {
        return parameters;
    }

    public BlockStmnt body() {
        return body;
    }

    public Environment makeEnv() {
        return new Env(env);
    }

    public String toString() {
        return "<fun: " + hashCode() + ">";
    }
}