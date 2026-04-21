import java.util.Map;

public interface DictionaryService {
    String getAll();
    String find(String key);
    boolean remove(String key);
    void add(String key, String value);
}