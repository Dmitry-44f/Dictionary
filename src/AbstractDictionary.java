import java.io.*;
import java.util.*;

abstract class AbstractDictionary implements DictionaryService {
    protected final FileRepository repository;

    public AbstractDictionary(String filePath) {
        this.repository = new FileRepository(filePath);
    }

    protected abstract boolean isValidKey(String key);

    @Override
    public String getAll() {
        StringBuilder sb = new StringBuilder();
        Map<String, String> allData = repository.getDictionary();

        for (Map.Entry<String, String> entry: allData.entrySet()) {
            String key = entry.getKey();
            if (isValidKey(key)) {
                sb.append(key).append(" - ").append(entry.getValue()).append("\n");
            }
        }

        return sb.toString();
    }

    @Override
    public String find(String key) throws Exception {
        if (!isValidKey(key)) {
            throw new Exception("Ключ не соотвествует выбранному словарю!");
        }

        String value = repository.getValue(key);
        if (value == null) {
            throw new Exception("Запись с данным ключок не найдена!");
        }
        return value;
    }

    @Override
    public boolean remove(String key) {
        if (isValidKey(key)) {
            return repository.removeEntry(key);
        }
        return false;
    }

    @Override
    public void add(String key, String value) throws Exception {
        if (!isValidKey(key)) {
            throw new Exception("Ключ не соответствует требованиям словаря!");
        }

        repository.addEntry(key, value);
    }
}
