import java.io.*;
import java.util.*;

abstract class AbstractDictionary implements DictionaryService {
    protected final String filePath;
    protected Map<String, String> data = new HashMap<>();

    protected List<String> invalidLines = new ArrayList<>(); // для пар не подходящих выбранному словарю

    public AbstractDictionary(String filePath) {
        this.filePath = filePath;
        loadFromFile();
    }

    protected abstract boolean isValidKey(String key);

    private void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // пустая строка

                String[] parts = line.split("-");

                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    if (isValidKey(key)) {
                        data.put(key, value);
                    } else {
                        invalidLines.add(line);
                    }
                } else {
                    invalidLines.add(line);
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

            // добавляем не подходящие выбранному словарю записи
            for  (String invL: invalidLines) {
                bw.write(invL);
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
    public boolean remove(String key) {
        if (data.remove(key) != null) {
            saveToFile();
            return true;
        }
        return false;
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
