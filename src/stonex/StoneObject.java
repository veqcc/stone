package stonex;

public class StoneObject {
    private Environment env;

    public StoneObject(Environment e) {
        env = e;
    }

    public String toString() {
        return "object: " + hashCode() + ">";
    }

    public Object read(String member) throws Exception {
        return getEnv(member).get(member);
    }

    public void write(String member, Object value) throws Exception {
        getEnv(member).putNew(member, value);
    }

    private Environment getEnv(String member) throws Exception {
        Environment e = env.where(member);
        if (e != null && e == env) {
            return e;
        } else {
            throw new Exception();
        }
    }
}