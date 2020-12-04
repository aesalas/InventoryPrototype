package mainJava;

import java.sql.SQLException;

public interface Database {
    void addToDB(int amountToAdd) throws SQLException;
    void removeFromDB(int id);
    void editDB();
    String printDB();
}
