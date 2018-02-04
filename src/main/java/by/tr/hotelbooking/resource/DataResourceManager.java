package by.tr.hotelbooking.resource;

import java.util.ResourceBundle;

public final class DataResourceManager {
    private final static DataResourceManager INSTANCE = new DataResourceManager();
    private ResourceBundle bundle = ResourceBundle.getBundle("parameter");

    private DataResourceManager() {

    }

    public static DataResourceManager getInstance(){
        return INSTANCE;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
