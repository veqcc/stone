package stonex;
import java.lang.reflect.Method;
import javax.swing.JOptionPane;

public class Natives {
    public Environment environment(Environment env) throws Exception {
        appendNatives(env);
        return env;
    }

    private void appendNatives(Environment env) throws Exception {
        append(env, "print", Natives.class, "print", Object.class);
        append(env, "read", Natives.class, "read");
        append(env, "length", Natives.class, "length", String.class);
        append(env, "toInt", Natives.class, "toInt", Object.class);
        append(env, "currentTime", Natives.class, "currentTime");
    }

    private void append(Environment env, String name, Class<?> clazz, String methodName, Class<?> ... params) throws Exception {
        Method m;
        try {
            m = clazz.getMethod(methodName, params);
        } catch (Exception e) {
            throw e;
        }

        env.put(name, new NativeFunction(m));
    }

    public static int print(Object obj) {
        System.out.println(obj.toString());
        return 0;
    }

    public static String read() {
        return JOptionPane.showInputDialog(null);
    }

    public static int length(String s) {
        return s.length();
    }

    public static int toInt(Object value) {
        if (value instanceof String) {
            return Integer.parseInt((String)value);
        } else if (value instanceof Integer) {
            return (Integer)value;
        } else {
            throw new NumberFormatException(value.toString());
        }
    }

    private static long startTime = System.currentTimeMillis();
    public static int currentTime() {
        return (int)(System.currentTimeMillis() - startTime);
    }
}