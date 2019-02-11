package stonex;
import java.lang.reflect.Method;

public class NativeFunction {
    private Method method;
    private int numParams;

    public NativeFunction(Method m) {
        method = m;
        numParams = m.getParameterTypes().length;
    }

    public String toString() {
        return "<native: " + hashCode() + ">";
    }

    public int numOfParameters() {
        return numParams;
    }

    public Object invoke(Object[] args) throws Exception {
        try {
            return method.invoke(null, args);
        } catch (Exception e) {
            throw e;
        }
    }
}