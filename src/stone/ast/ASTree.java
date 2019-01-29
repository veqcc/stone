package stone.ast;

import stone.Environment;
import java.util.Iterator;

public abstract class ASTree implements Iterable<ASTree> {
    public abstract ASTree child(int i);

    public abstract int numChildren();

    public abstract Iterator<ASTree> children();

    public abstract String location();

    public abstract Object eval(Environment env);

    public Iterator<ASTree> iterator() {
        return children();
    }
}
