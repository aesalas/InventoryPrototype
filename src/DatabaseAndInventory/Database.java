package DatabaseAndInventory;

import java.sql.SQLException;

public interface Database {
    void addToDB(String a, String b, String c, String d, double e) throws SQLException;
    void removeFromDB(int id);
    void editDB();
    String searchDB(int id);
    String printDB();
}
