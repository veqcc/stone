package stonex;

public interface Environment {
    Symbols symbols() throws Exception;

    void put(String name, Object value) throws Exception;

    void put(int nest, int index, Object value) throws Exception;

    Object get(String name) throws Exception;

    Object get(int nest, int index);

    void putNew(String name, Object value) throws Exception;

    Environment where(String name) throws Exception;
}