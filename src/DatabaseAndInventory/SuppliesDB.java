package DatabaseAndInventory;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class SuppliesDB implements Database{
    Connection conn;
    Scanner scan = new Scanner(System.in);

    /**
     * creates the supplies database if not yet created
     */
    public void createSuppliesDB() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS Supplies (\n" +
                "    itemId       INTEGER        PRIMARY KEY,\n" +
                "    itemName VARCHAR,\n" +
                "    ExpireDate  VARCHAR,\n" +
                "    quantity NUMERIC,\n" +
                "    category  VARCHAR,\n" +
                "    unitPrice   NUMERIC (6, 2)\n" +
                ");");
        stmt.close();
    }

    /**
     * Adds new supplies to database
     * @param name the amount of items the user wants to add
     * @throws SQLException
     */
    public void addToDB(String name, String category, String expireDate, String quantity, double unitPrice) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
        Statement selectStmt = conn.createStatement();
        String nameInserts;

        int id = selectStmt.executeQuery("SELECT itemId FROM Supplies ORDER BY itemId DESC LIMIT 1;")
                .getInt("itemId") + 1;

        nameInserts = "INSERT INTO Supplies (itemId, itemName, expireDate, quantity, category, unitPrice) VALUES "
                + "(?, ?, ?, ?, ?, ?);";

        PreparedStatement pstmt = conn.prepareStatement(nameInserts);
        pstmt.setInt(1, id);
        pstmt.setString(2, name);
        pstmt.setString(3, expireDate);
        pstmt.setString(4, category);
        pstmt.setInt(5, Integer.parseInt(quantity));
        pstmt.setString(6, String.valueOf(unitPrice));
        pstmt.executeUpdate();

        pstmt.close();
    }

    /**
     *  Searches through database based on the name entered by user
     * @return employee id of first match
     */
    public String searchDB(int itemId) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
        PreparedStatement pstmt = conn.prepareStatement("SELECT itemId FROM Supplies WHERE itemId = ?");
        pstmt.setInt(1, itemId);
        ResultSet nameRS = pstmt.executeQuery();
        if(nameRS.getInt("itemId") == itemId) {
            return "exists";
        }else {
            return "error";
        }
    }

    /**
     * Edits supply database based on the category and id using user input
     */
    public void editDB(int idToEdit, String fieldToEdit, String newValue) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Equipment");
        if(fieldToEdit.equalsIgnoreCase("name")){
            pstmt = conn.prepareStatement("UPDATE Equipment SET itemName = ? WHERE itemId = ?;");
        }else if(fieldToEdit.equalsIgnoreCase("quantity")){
            pstmt = conn.prepareStatement("UPDATE Equipment SET quantity = ? WHERE itemId = ?;");
        }else if(fieldToEdit.equalsIgnoreCase("category")){
            pstmt = conn.prepareStatement("UPDATE Equipment SET category = ? WHERE itemId = ?;");
        }else if (fieldToEdit.equalsIgnoreCase("unit price")){
            pstmt = conn.prepareStatement("UPDATE Equipment SET unitPrice = ? WHERE itemId = ?;");

        }
        pstmt.setString(1, newValue);
        pstmt.setInt(2, idToEdit);
        pstmt.executeUpdate();
    }

    /**
     * Removes supply from database based on passed id
     * @param itemId id of item to be removed
     */
    public void removeFromDB(int itemId) throws SQLException{
        conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Supply WHERE itemId = ?");
        pstmt.setInt(1, itemId);
        pstmt.executeUpdate();
    }

    /**
     * Prints entire Database
     */
    public String printDB() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Supplies ORDER BY itemId");
        ResultSet nameRS = pstmt.executeQuery();
        StringBuilder dbPrint = new StringBuilder();
        while (nameRS.next()) {
            if(nameRS.isFirst()){
                dbPrint = new StringBuilder("------------------------------------------\n");
            }else {
                dbPrint.append("Item: ").append(nameRS.getString("itemName"))
                        .append(" (").append(nameRS.getInt("itemId"))
                        .append("), expires: ").append(nameRS.getString("expireDate"))
                        .append(", quantity remaining: ").append(nameRS.getInt("quantity"))
                        .append(", category: ").append(nameRS.getString("category"))
                        .append(", unit price: ").append(nameRS.getString("unitPrice"))
                        .append("\n");
            }
        }
        dbPrint.append("-------------------------------------------");
        return dbPrint.toString();
    }
}
