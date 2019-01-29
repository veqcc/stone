package stone;
import java.util.HashMap;

public class BasicEnv implements Environment {
    protected HashMap<String, Object> values;

    public BasicEnv() {
        values = new HashMap<String, Object>();
    }

    public void put(String name, Object value) {
        values.put(name, value);
    }

    public Object get(String name) {
        return values.get(name);
    }

    public void putNew(String name, Object value) {
        //
    }

    public Environment where(String name) {
        return this;
    }

    public void setOuter(Environment e) {
        // なぜかこれを入れないとコンパイルエラー。
    }
}