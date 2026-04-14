public class LatinDictionary extends AbstractDictionary {
    public LatinDictionary(String filePath) throws Exception {
        super(filePath);
    }

    @Override
    protected boolean isValidKey(String key) {
        return key.length() == 4 && key.matches("^[a-zA-Z]+$");
    }
}
