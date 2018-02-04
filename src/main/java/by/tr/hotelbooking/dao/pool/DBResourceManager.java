package by.tr.hotelbooking.dao.pool;


import java.util.ResourceBundle;

public final class DBResourceManager {
    private static final DBResourceManager INSTANCE = new DBResourceManager();
    private ResourceBundle bundle = ResourceBundle.getBundle("db");

    private DBResourceManager() {
    }

    public static DBResourceManager getInstance() {
        return INSTANCE;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
