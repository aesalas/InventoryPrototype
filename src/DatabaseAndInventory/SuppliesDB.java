package DatabaseAndInventory;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class SuppliesDB implements Database{
    Connection conn;
    Scanner scan = new Scanner(System.in);

    /**
     * creates the equipment database if not yet created
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
        LocalDate date = LocalDate.now();

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

            System.out.println("------------------------------------------");

    }

    /**
     *  Searches through database based on the name entered by user
     * @return employee id of first match
     */
    public int searchDB(){
        int itemId = 0;
        int count = 0;
        System.out.println("What is the item name would you like to search for: ");
        String searchName = scan.nextLine();
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
            PreparedStatement pstmt = conn.prepareStatement("SELECT itemId, itemName FROM Equipment WHERE itemName LIKE ?");
            pstmt.setString(1, searchName + "%");
            ResultSet nameRS = pstmt.executeQuery();

            while (nameRS.next()) {
                System.out.println("Possible match: "
                        + nameRS.getString("itemName") + " ("
                        + nameRS.getInt("itemId")+ ")");
                count++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Edits equipment database based on the category and id using user input
     * Uses searchDB() to get item id
     */
    public void editDB(){
        int idToEdit = searchDB();
        String nameInserts;
        String valueToChangeTo;
        System.out.println("What would you like to change? (Item name, quantity, category, or unit price)");
        String type = scan.nextLine();
        System.out.println("What would you like to change it to?");
        valueToChangeTo = scan.next();
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Equipment");

            if(type.equalsIgnoreCase("name")){
                nameInserts = "UPDATE Equipment SET itemName = ? WHERE itemId = ?;";
                pstmt = conn.prepareStatement(nameInserts);
                pstmt.setString(1, valueToChangeTo);
                pstmt.setInt(2, idToEdit);
            }else if(type.equalsIgnoreCase("quantity")){
                nameInserts = "UPDATE Equipment SET quantity = ? WHERE itemId = ?;";
                pstmt = conn.prepareStatement(nameInserts);
                pstmt.setString(1, valueToChangeTo);
                pstmt.setInt(2, idToEdit);
            }else if(type.equalsIgnoreCase("category")){
                nameInserts = "UPDATE Equipment SET category = ? WHERE itemId = ?;";
                pstmt = conn.prepareStatement(nameInserts);
                pstmt.setString(1, valueToChangeTo);
                pstmt.setInt(2, idToEdit);
            }else if (type.equalsIgnoreCase("unit price")){
                nameInserts = "UPDATE Equipment SET unitPrice = ? WHERE itemId = ?;";
                pstmt = conn.prepareStatement(nameInserts);
                pstmt.setString(1, valueToChangeTo);
                pstmt.setInt(2, idToEdit);
            }else{
                System.out.println("invalid input");
            }
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public String searchDB(int id) {
        return null;
    }

    /**
     * Removes equipment from database based on passed id
     * @param itemId id of item to be removed
     */
    public void removeFromDB(int itemId){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Equipment WHERE itemId = ?");
            pstmt.setInt(1, itemId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints entire Database
     */
    public String printDB(){
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
