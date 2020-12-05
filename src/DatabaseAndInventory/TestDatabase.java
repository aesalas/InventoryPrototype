package DatabaseAndInventory;

import java.sql.SQLException;
import java.util.Scanner;

public class TestDatabase {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        SuppliesDB abc = new SuppliesDB();
        EmployeeDB a = new EmployeeDB();

        a.createEmployeeDB();
        abc.createSuppliesDB();

    }
}
