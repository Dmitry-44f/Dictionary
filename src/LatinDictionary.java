public class LatinDictionary extends AbstractDictionary {
    public LatinDictionary(String filePath) {
        super(filePath);
    }

    @Override
    protected boolean isValidKey(String key) {
        return key.matches("[a-zA-Z]{4}");
    }
}
