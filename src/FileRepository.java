import java.io.*;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class FileRepository {
    private final String fileName;
    private final Map<String, String> dictionary = new TreeMap<>();

    public FileRepository(String fileName) {
        this.fileName = fileName;
        readFile();
    }

    private void readFile() {
        File file = new File(fileName);

        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" - ", 2);
                if (parts.length == 2) {
                    dictionary.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private void saveFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, String> entry: dictionary.entrySet()) {
                bw.write(entry.getKey() + " - " + entry.getValue());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Ошибка сохранения файла: " + e.getMessage());
        }
    }

    public String getValue(String key) {
        return dictionary.get(key);
    }

    public Map<String, String> getDictionary() {
        return Collections.unmodifiableMap(dictionary);
    }

    public void addEntry(String key, String value) {
        dictionary.put(key, value);
        saveFile();
    }

    public boolean removeEntry(String key) {
        if (dictionary.remove(key) != null) {
            saveFile();
            return true;
        }
        return false;
    }
}
