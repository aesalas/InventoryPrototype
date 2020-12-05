package DatabaseAndInventory;

import java.sql.SQLException;

public interface Database {
    void addToDB(String a, String b, String c, String d, double e) throws SQLException;
    void removeFromDB(int id) throws SQLException;
    public void editDB(int idToEdit, String fieldToEdit, String newValue) throws SQLException;
    String searchDB(int id) throws SQLException;
    String printDB() throws SQLException;
}
