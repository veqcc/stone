package stonex;

import stonex.ast.BlockStmnt;
import stonex.ast.ParameterList;

public class Method extends Function {
    StoneObject self;

    public Method(ParameterList parameters, BlockStmnt body, Environment env, int size, StoneObject slef) {
        super(parameters, body, env, size);
        this.self = self;
    }

    @Override public Environment makeEnv() throws Exception {
        Env e = new Env(size, env);
        e.put(0, 0, self);
        return e;
    }
}