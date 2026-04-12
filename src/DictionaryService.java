import java.util.Map;

public interface DictionaryService {
    Map<String, String> getAll();
    String find(String key);
    void remove(String key);
    void add(String key, String value) throws Exception;
}