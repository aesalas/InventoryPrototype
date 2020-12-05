package DatabaseAndInventory;

import java.sql.SQLException;

public interface Database {
    void addToDB() throws SQLException;
    void removeFromDB(int id);
    void editDB();
    String searchDB(int id);
    String printDB();
}
