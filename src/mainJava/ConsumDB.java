package mainJava;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class ConsumDB {
    Connection conn;
    Scanner scan = new Scanner(System.in);
    String Expiration = "";
    LocalDate date = LocalDate.now();
    /**
     * creates inventory database for consumables
     * if not yet created
     */
    public void createConsumDB() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");

            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS Consumables (\n" +
                    "    itemId       INTEGER        PRIMARY KEY,\n" +
                    "    itemName VARCHAR,\n" +
                    "    expireDate VARCHAR,\n" +
                    "    quantity NUMERIC,\n" +
                    "    category  VARCHAR,\n" +
                    "    unitPrice   NUMERIC (6, 2)\n" +
                    ");");
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Could not connect to database or create table");
            e.printStackTrace();
        }
    }

    /**
     * Adds new user-entered consumables to database
     * @param amountToAdd the amount of items the user wishes to add
     * @throws SQLException
     */
    public void addToDB(int amountToAdd) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
        Statement selectStmt = conn.createStatement();
        String nameInserts;

        int id = selectStmt.executeQuery("SELECT itemId FROM Consumables ORDER BY itemId DESC LIMIT 1;")
                .getInt("itemId") + 1;

        nameInserts = "INSERT INTO Consumables (itemId, itemName, expireDate, quantity, category, unitPrice) VALUES "
                + "(?, ?, ?, ?, ?, ?);";
        for (int i = 0; i < amountToAdd; i++) {
            String name = "";
            String category = "";
            int quantity = 0;
            double unitPrice = 0;

            System.out.println("Enter item name:");
            name = scan.nextLine();
            System.out.println("Enter the expiration date:");
            this.Expiration = scan.nextLine();
            System.out.println("Enter item category:");
            category = scan.nextLine();
            System.out.println("Enter item quantity:");
            quantity = scan.nextInt();
            System.out.println("Enter item unit price:");
            unitPrice = scan.nextDouble();
            scan.nextLine();
            try {
                PreparedStatement pstmt = conn.prepareStatement(nameInserts);
                pstmt.setInt(1, id + i);
                pstmt.setString(2, name);
                pstmt.setString(3, this.Expiration);
                pstmt.setString(4, category);
                pstmt.setInt(5, quantity);
                pstmt.setString(6, String.valueOf(unitPrice));
                pstmt.executeUpdate();

                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("------------------------------------------");
        }
    }

    /**
     *  Searches through database based on the item entered by the user
     * @return item id of first match
     */
    public int searchDB(){
        int itemId = 0;
        System.out.println("What name would you like to search for: ");
        String searchName = scan.nextLine();
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
            PreparedStatement pstmt = conn.prepareStatement("SELECT itemId, itemName FROM Consumables WHERE itemName LIKE ?");
            pstmt.setString(1, searchName + "%");
            ResultSet nameRS = pstmt.executeQuery();

            while (nameRS.next()) {
                System.out.println("Possible match: "
                        + nameRS.getString("itemName") + " ("
                        + nameRS.getInt("itemId")+ ")");
                itemId = nameRS.getInt("itemId");
            }
            return itemId;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Edits database based on the category entered by the user
     * Uses searchDB() to get the item id that needs changing
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
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Consumables");

            if(type.equalsIgnoreCase("name")){
                nameInserts = "UPDATE Consumables SET itemName = ? WHERE itemId = ?;";
                pstmt = conn.prepareStatement(nameInserts);
                pstmt.setString(1, valueToChangeTo);
                pstmt.setInt(2, idToEdit);

            }else if(type.equalsIgnoreCase("quantity")){
                nameInserts = "UPDATE Consumables SET quantity = ? WHERE itemId = ?;";
                pstmt = conn.prepareStatement(nameInserts);
                pstmt.setString(1, valueToChangeTo);
                pstmt.setInt(2, idToEdit);
            }else if(type.equalsIgnoreCase("category")){
                nameInserts = "UPDATE Consumables SET category = ? WHERE itemId = ?;";
                pstmt = conn.prepareStatement(nameInserts);
                pstmt.setString(1, valueToChangeTo);
                pstmt.setInt(2, idToEdit);
            }else if (type.equalsIgnoreCase("unit price")){
                nameInserts = "UPDATE Consumables SET unitPrice = ? WHERE itemId = ?;";
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

    /**
     * Removes item from database based on passed in id
     * @param itemId id of item to be removed
     */
    public void removeFromDB(int itemId){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Consumables WHERE itemId = ?");
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
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Consumables ORDER BY itemId");
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
