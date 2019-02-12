package stonex.ast;
import stonex.Environment;

import java.util.List;

public class ArrayRef extends Postfix {
    public ArrayRef(List<ASTree> c) {
        super(c);
    }

    public ASTree index() {
        return child(0);
    }

    public String toString() {
        return "[" + index() + "]";
    }

    public Object eval(Environment env, Object value) throws Exception {
        if (value instanceof Object[]) {
            Object index = index().eval(env);
            if (index instanceof Integer) {
                return ((Object[])value)[(Integer)index];
            }
        }
        throw new Exception();
    }
}