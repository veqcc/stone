package stonex;

public class StoneObject {
    private Environment env;
    private ClassInfo classinfo;
    private Object[] fields;

    public StoneObject(Environment e) {
        env = e;
    }

    public StoneObject(ClassInfo ci, int size) {
        classinfo = ci;
        fields = new Object[size];
    }

    public ClassInfo classInfo() {
        return classinfo;
    }

    public String toString() {
        return "object: " + hashCode() + ">";
    }

    public Object read(String name) throws Exception {
        Integer i = classinfo.fieldIndex(name);
        if (i != null) {
            return fields[i];
        } else {
            i = classinfo.methodIndex(name);
            if (i != null) {
                return method(i);
            }
        }
        throw new Exception();
    }

    public void write(String name, Object value) throws Exception {
        Integer i = classinfo.fieldIndex(name);
        if (i == null) {
            throw new Exception();
        } else {
            fields[i] = value;
        }
    }

    public Object read(int index) {
        return fields[index];
    }

    public void write(int index, Object value) {
        fields[index] = value;
    }

    public Object method(int index) {
        return classinfo.method(this, index);
    }
}