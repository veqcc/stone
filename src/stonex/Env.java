package stonex;
import java.util.HashMap;

public class Env implements Environment {
    protected Environment outer;
    protected Object[] values;

    public Env(int size, Environment env) {
        values = new Object[size];
        outer = env;
    }

    public Symbols symbols() throws Exception {
        throw new Exception();
    }

    public Object get(int nest, int index) {
        if (nest == 0) {
            return values[index];
        } else if (outer == null) {
            return null;
        } else {
            return outer.get(nest - 1, index);
        }
    }

    public void put(int nest, int index, Object value) throws Exception {
        if (nest == 0) {
            values[index] = value;
        } else if (outer == null) {
            throw new Exception();
        } else {
            outer.put(nest - 1, index, value);
        }
    }

    public Object get(String name) throws Exception {
        error(name);
        return null;
    }

    public void put(String name, Object value) throws Exception {
        error(name);
    }

    public void putNew(String name, Object value) throws Exception {
        error(name);
    }

    public Environment where(String name) throws Exception {
        error(name); return null;
    }

    public void setOuter(Environment e) {
        outer = e;
    }

    private void error(String name) throws Exception {
        throw new Exception();
    }
}