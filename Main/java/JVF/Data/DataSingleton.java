package JVF.Data;
public class DataSingleton {

    private int userId;
    private DataSingleton(){}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private static class SingletonHelper {
        private static final DataSingleton INSTANCE = new DataSingleton();
    }

    public static DataSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
