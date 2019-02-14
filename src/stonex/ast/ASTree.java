package stonex.ast;
import stonex.Environment;
import stonex.Symbols;
import java.util.Iterator;

public abstract class ASTree implements Iterable<ASTree> {
    public abstract ASTree child(int i);

    public abstract int numChildren();

    public abstract Iterator<ASTree> children();

    public abstract String location();

    public Iterator<ASTree> iterator() {
        return children();
    }

    public abstract Object eval(Environment env) throws Exception;

    public void lookup(Symbols syms) throws Exception {}
}