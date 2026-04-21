import java.util.Map;

public interface DictionaryService {
    String getAll();
    String find(String key) throws Exception;
    boolean remove(String key);
    void add(String key, String value) throws Exception;
}