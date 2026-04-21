public class NumericDictionary extends AbstractDictionary {
    public NumericDictionary(String filePath) {
        super(filePath);
    }

    protected boolean isValidKey(String key) {
        return key.matches("[0-9]{5}");
    }
}
