package DatabaseAndInventory;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Pattern;

public class EmployeeDB implements Database {

    Connection conn;
    // Password constraints for regex
    String PASSWORD_COMBO = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!.,@#]).{8,20}$";
    // owner id for access to certain pages, employees cant access
    int OWNER_ID = 12345;

    /**
     * Creates the employee database if not yet created.
     * Employee database should have the following:
     *  employee id, first name, last name,
     *  starting date (automatically set to day employee was added),
     *  password (with constraints listed above), job title, and pay rate
     */
    public void createEmployeeDB() throws SQLException {
            conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS Employee (\n" +
                    "    eid       INTEGER        PRIMARY KEY,\n" +
                    "    firstName VARCHAR,\n" +
                    "    lastName  VARCHAR,\n" +
                    "    startDate VARCHAR,\n" +
                    "    password  VARCHAR,\n" +
                    "    jobTitle  VARCHAR,\n" +
                    "    payrate   NUMERIC (6, 2)\n" +
                    ");");
            stmt.close();
    }

    /**
     * Adds employee(s) to database
     * @throws SQLException
     */
    public void addToDB(String name, String password, String jobTitle, double payrate) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
        Statement selectStmt = conn.createStatement();
        String nameInserts;

        // local date for starting date
        LocalDate date = LocalDate.now();

        int id = selectStmt.executeQuery("SELECT eid FROM Employee ORDER BY eid DESC LIMIT 1;")
                    .getInt("eid") + 1;

        nameInserts = "INSERT INTO Employee (eid, firstName, lastName, startDate, password, jobTitle, payrate) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?);";


            while(!Pattern.matches(PASSWORD_COMBO,password)){
                System.out.println("Password invalid. Make sure it is greater than 8 characters.");
                System.out.println("Create password: ");

            }
            String[] firstandLast = name.split(" ");
            String firstName = firstandLast[0];
            String lastName = firstandLast[1];

            PreparedStatement pstmt = conn.prepareStatement(nameInserts);
            pstmt.setInt(1, id);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, String.valueOf(date));
            pstmt.setString(5, password);
            pstmt.setString(6, jobTitle);
            pstmt.setString(7, String.valueOf(payrate));
            pstmt.executeUpdate();

            pstmt.close();

    }

    /**
     *  Searches through database based on eid entered at sign in
     * @return boolean
     * @param eid
     */
    public String searchDB(int eid){
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
                PreparedStatement pstmt = conn.prepareStatement("SELECT eid, password FROM Employee WHERE eid = ?");
                pstmt.setString(1,  eid + "%");
                ResultSet nameRS = pstmt.executeQuery();

                if(nameRS.getInt("eid") == eid) {

                        if(nameRS.getInt("eid") == OWNER_ID) {
                            return "own";
                        }else{
                            return "emp";
                        }

                }else {
                    return "";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return "";
    }

    /**
     * Edits employee database based on the category entered by the user
     *
     */
    public void editDB(){
        int eidToEdit = 123456;
        String nameInserts = "";
        String valueToChangeTo = "";
        System.out.println("What would you like to change? (first name, last name, job title, or pay rate)");
        String type = scan.nextLine();
        System.out.println("What would you like to change it to?");
        valueToChangeTo = scan.next();
            try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Employee");

                //ResultSet nameRS = pstmt.executeQuery();
            if(type.equalsIgnoreCase("first name")){
                nameInserts = "UPDATE Employee SET firstName = ? WHERE eid = ?;";
                pstmt = conn.prepareStatement(nameInserts);
                pstmt.setString(1, valueToChangeTo);
                pstmt.setInt(2, eidToEdit);

            }else if(type.equalsIgnoreCase("last name")){
                nameInserts = "UPDATE Employee SET lastName = ? WHERE eid = ?;";
                pstmt = conn.prepareStatement(nameInserts);
                pstmt.setString(1, valueToChangeTo);
                pstmt.setInt(2, eidToEdit);
            }else if(type.equalsIgnoreCase("job title")){
                nameInserts = "UPDATE Employee SET jobTitle = ? WHERE eid = ?;";
                pstmt = conn.prepareStatement(nameInserts);
                pstmt.setString(1, valueToChangeTo);
                pstmt.setInt(2, eidToEdit);
            }else if (type.equalsIgnoreCase("pay rate")){
                nameInserts = "UPDATE Employee SET payrate = ? WHERE eid = ?;";
                pstmt = conn.prepareStatement(nameInserts);
                pstmt.setString(1, valueToChangeTo);
                pstmt.setInt(2, eidToEdit);
            }else{
                System.out.println("invalid input");
            }
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Removes employee from database based on passed id
     * @param eid id of employee to be removed
     */
    public void removeFromDB(int eid){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Employee WHERE eid = ?");
            pstmt.setInt(1, eid);
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
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Employee ORDER BY eid");
            ResultSet nameRS = pstmt.executeQuery();
            StringBuilder dbPrint = new StringBuilder();
            while (nameRS.next()) {
                if(nameRS.isFirst()){
                    dbPrint = new StringBuilder(("------------------------------------------\n"));
                }else {
                    dbPrint.append("Employee: ").append(nameRS.getString("firstName")).append(" ").append(nameRS.getString("lastName"))
                            .append(" (").append(nameRS.getInt("eid"))
                            .append("), started: ").append(nameRS.getString("startDate"))
                            .append(", title: ").append(nameRS.getString("jobTitle"))
                            .append(", payrate: ").append(nameRS.getString("payrate"))
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

    /**
     * @param password and eid entered in SignIn.fxml to check
     *                 if the password is correct
     * @return true if password is correct, false if it is not
     * @throws SQLException
     */
    public boolean checkPassword(String password, int eid) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src/data/Inventory.db");
        PreparedStatement pstmt = conn.prepareStatement("SELECT password FROM Employee WHERE eid = ?");
        pstmt.setString(1,  eid + "%");
        ResultSet passwordRS = pstmt.executeQuery();

        if(passwordRS.getString("password").equals(password)){
            return true;
        }else{
            return false;
        }
    }

}