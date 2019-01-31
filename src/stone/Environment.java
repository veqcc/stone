package stone;

public interface Environment {
    Symbols symbols();

    void put(String name, Object value);

    void put(int nest, int index, Object value);

    Object get(String name);

    Object get(int nest, int index);

    void putNew(String name, Object value);

    Environment where(String name);

    void setOuter(Environment e);
}
