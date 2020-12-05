package DatabaseAndInventory;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class OrderDB implements Database {
    Connection conn;
    Scanner scan = new Scanner(System.in);

    /**
     * creates database for purchase orders if not yet created
     */
    public void createOrderDB() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");

            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS PurchaseOrder (\n" +
                    "    orderId       INTEGER        PRIMARY KEY,\n" +
                    "    datePlaced VARCHAR,\n" +
                    "    placedBy VARCHAR,\n" +
                    "    quantity NUMERIC,\n" +
                    "    itemsOrdered  VARCHAR,\n" +
                    "    totalCost   NUMERIC (6, 2)\n" +
                    ");");
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Could not connect to database or create table");
            e.printStackTrace();
        }
    }

    /**
     * Adds new purchase order to database
     * @param placedBy the amount of items the user wishes to add
     * @throws SQLException
     */
    public void addToDB(String placedBy, String datePlaced, String itemsOrdered, String quantity, double totalcost) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
        Statement selectStmt = conn.createStatement();
        String nameInserts;

        int id = selectStmt.executeQuery("SELECT orderId FROM PurchaseOrder ORDER BY orderId DESC LIMIT 1;")
                .getInt("orderId") + 1;

        nameInserts = "INSERT INTO PurchaseOrder (orderId, datePlaced, placedBy, quantity, itemsOrdered, totalCost) VALUES "
                + "(?, ?, ?, ?, ?, ?);";

        PreparedStatement pstmt = conn.prepareStatement(nameInserts);
        pstmt.setInt(1, id);
        pstmt.setString(2, datePlaced);
        pstmt.setString(3, placedBy);
        pstmt.setInt(4, Integer.parseInt(quantity));
        pstmt.setString(5, itemsOrdered);
        pstmt.setString(6, String.valueOf(totalcost));
        pstmt.executeUpdate();

        pstmt.close();
    }

    /**
     *  Searches through database based on orderId entered
     * @param orderId the id to be found
     * @return if the order exists or not
     */
    public String searchDB(int orderId) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
        PreparedStatement pstmt = conn.prepareStatement("SELECT orderId, password FROM PurchaseOrder WHERE orderId = ?");
        pstmt.setInt(1, orderId);
        ResultSet nameRS = pstmt.executeQuery();

        // Checks if exists
        if(nameRS.getInt("eid") == orderId) {
            return "exists";
        }else {
            return "error";
        }
    }

    /**
     * Edits database based on the category entered by the user
     */
    public void editDB(int idToEdit, String fieldToEdit, String newValue) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM PurchaseOrder");

        if(fieldToEdit.equalsIgnoreCase("Name")){
            pstmt = conn.prepareStatement("UPDATE PurchaseOrder SET itemName = ? WHERE itemId = ?;");
        }else if(fieldToEdit.equalsIgnoreCase("quantity")){
            pstmt = conn.prepareStatement("UPDATE PurchaseOrder SET quantity = ? WHERE itemId = ?;");
        }else if(fieldToEdit.equalsIgnoreCase("category")){
            pstmt = conn.prepareStatement("UPDATE PurchaseOrder SET category = ? WHERE itemId = ?;");
        }else if (fieldToEdit.equalsIgnoreCase("totalCost")){
            pstmt = conn.prepareStatement("UPDATE PurchaseOrder SET unitPrice = ? WHERE itemId = ?;");
        }
        pstmt.setString(1, newValue);
        pstmt.setInt(2, idToEdit);
        pstmt.executeUpdate();

    }

    /**
     * Removes item from database based on passed in id
     * @param orderId id of order to be removed
     */
    public void removeFromDB(int orderId) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM PurchaseOrder WHERE orderId = ?");
        pstmt.setInt(1, orderId);
        pstmt.executeUpdate();
    }

    /**
     * Prints entire Database
     */
    public String printDB() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM PurchaseOrder ORDER BY orderId");
        ResultSet nameRS = pstmt.executeQuery();
        StringBuilder dbPrint = new StringBuilder();
        while (nameRS.next()) {
            if(nameRS.isFirst()){
                dbPrint = new StringBuilder("------------------------------------------\n");
            }else {
                dbPrint.append("Order id: ").append(nameRS.getString("orderId"))
                        .append(", placed on: ").append(nameRS.getInt("datePlaced"))
                        .append(", placed by: ").append(nameRS.getString("placedBy"))
                        .append(", total items ordered: ").append(nameRS.getInt("quantity"))
                        .append(", list of items: ").append(nameRS.getString("itemsOrdered"))
                        .append(", total cost: ").append(nameRS.getString("totalCost"))
                        .append("\n");
            }
        }
        dbPrint.append("-------------------------------------------");
        return dbPrint.toString();
    }
}
