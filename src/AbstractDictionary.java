import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

abstract class AbstractDictionary implements DictionaryService {
    protected final String filePath;
    protected Map<String, String> data = new HashMap<>();

    public AbstractDictionary(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Не найден файл: " + filePath);
        }

        this.filePath = filePath;

    }

    protected abstract boolean isValidKey(String key);

    private void loadFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("-");

                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    if (isValidKey(key)) {
                        data.put(key, value);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка чтения:" + e.getMessage());
        }
    }

    protected void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry: data.entrySet()) {
                String line = entry.getKey() + " - " + entry.getValue();
                bw.write(line);
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    @Override
    public Map<String, String> getAll() {
        return Collections.unmodifiableMap(data);
    }

    @Override
    public String find(String key) {
        return data.get(key);
    }

    @Override
    public void remove(String key) {
        if (data.remove(key) != null) {
            saveToFile();
        }
    }

    @Override
    public void add(String key, String value) throws Exception {
        if (isValidKey(key)) {
            data.put(key, value);
            saveToFile();
        } else {
            throw new Exception("Ключ не соответствует требованиям!");
        }
    }
}
