package mainJava;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Pattern;

public class EmployeeDB implements Database {
    Connection conn;
    Scanner scan = new Scanner(System.in);
    String PASSWORD_COMBO = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!.,@#]).{8,20}$";

    /**
     * creates the employee database if not yet created
     */
    public void createEmployeeDB() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/data/inventories.db");

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
        } catch (SQLException e) {
            System.out.println("Could not connect to database or create table");
            e.printStackTrace();
        }
    }

    /**
     * Adds employee(s) to database
     * @param amountToAdd the amount of employees the user wishes to add
     * @throws SQLException
     */
    public void addToDB(int amountToAdd) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src/data/inventories.db");
        Statement selectStmt = conn.createStatement();
        String nameInserts;
        LocalDate date = LocalDate.now();


        int id = selectStmt.executeQuery("SELECT eid FROM Employee ORDER BY eid DESC LIMIT 1;")
                    .getInt("eid") + 1;

        nameInserts = "INSERT INTO Employee (eid, firstName, lastName, startDate, password, jobTitle, payrate) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?);";
        for (int i = 0; i < amountToAdd; i++) {
            String firstName = "";
            String lastName = "";
            String password = "";
            String jobTitle = "";
            double payrate = 0;

            System.out.println("Enter first name:");
            firstName = scan.nextLine();
            System.out.println("Enter last name");
            lastName = scan.nextLine();
            System.out.println("Enter job title:");
            jobTitle = scan.nextLine();
            System.out.println("Enter pay rate:");
            payrate = scan.nextDouble();
            scan.nextLine();
            System.out.println("Create password:");
            password = scan.nextLine();

            while(!Pattern.matches(PASSWORD_COMBO,password)){
                System.out.println("Password invalid. Make sure it is greater than 8 characters.");
                System.out.println("Create password: ");
                password = scan.nextLine();
            }

            try {
                PreparedStatement pstmt = conn.prepareStatement(nameInserts);
                pstmt.setInt(1, id + i);
                pstmt.setString(2, firstName);
                pstmt.setString(3, lastName);
                pstmt.setString(4, String.valueOf(date));
                pstmt.setString(5, password);
                pstmt.setString(6, jobTitle);
                pstmt.setString(7, String.valueOf(payrate));
                pstmt.executeUpdate();

                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("------------------------------------------");
        }
    }

    /**
     *  Searches through database based on the last name entered by the user
     * @return employee id of possible matches
     */
    public int searchDB(){
        int eidValues = 0;
        System.out.println("What last name would you like to search for: ");
        String searchName = scan.nextLine();
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:src/data/inventories.db");
                PreparedStatement pstmt = conn.prepareStatement("SELECT eid, firstName, lastName FROM Employee WHERE lastName LIKE ?");
                pstmt.setString(1, searchName + "%");
                ResultSet nameRS = pstmt.executeQuery();

                while (nameRS.next()) {
                    System.out.println("Possible match: "
                            + nameRS.getString("firstName") + " "
                            + nameRS.getString("lastName")+ " ("
                            +  nameRS.getInt("eid")+ ")");
                    eidValues = nameRS.getInt("eid");
                }
                return eidValues;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        return -1;
    }

    /**
     * Edits employee database based on the category entered by the user
     *
     */
    public void editDB(){
        int eidToEdit = searchDB();
        String nameInserts = "";
        String valueToChangeTo = "";
        System.out.println("What would you like to change? (first name, last name, job title, or pay rate)");
        String type = scan.nextLine();
        System.out.println("What would you like to change it to?");
        valueToChangeTo = scan.next();
            try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/data/inventories.db");
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
            conn = DriverManager.getConnection("jdbc:sqlite:src/data/inventories.db");
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
            conn = DriverManager.getConnection("jdbc:sqlite:src/data/inventories.db");
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

}