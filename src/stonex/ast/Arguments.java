package stonex.ast;
import stonex.Environment;
import stonex.Function;
import stonex.NativeFunction;
import java.util.List;

public class Arguments extends Postfix {
    public Arguments(List<ASTree> c) {
        super(c);
    }

    public int size() {
        return numChildren();
    }

    public Object eval(Environment callerEnv, Object value) throws Exception {
        if (value instanceof NativeFunction) {
            NativeFunction func = (NativeFunction)value;
            int nparams = func.numOfParameters();
            if (size() != nparams) {
                throw new Exception();
            }

            Object[] args = new Object[nparams];
            int num = 0;
            for (ASTree a: this) {
                args[num++] = a.eval(callerEnv);
            }

            return func.invoke(args);
        }

        if (value instanceof Function) {
            Function func = (Function)value;
            ParameterList params = func.parameters();
            if (size() != params.size()) {
                throw new Exception();
            }

            Environment newEnv = func.makeEnv();
            int num = 0;
            for (ASTree a: this) {
                params.eval(newEnv, num++, a.eval(callerEnv));
            }

            return func.body().eval(newEnv);
        }

        throw new Exception();
    }
}