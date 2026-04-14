public class NumericDictionary extends AbstractDictionary {
    public NumericDictionary(String filePath) throws Exception {
        super(filePath);
    }

    protected boolean isValidKey(String key) {
        return key.length() == 5 && key.matches("^[0-9]+$]");
    }
}
