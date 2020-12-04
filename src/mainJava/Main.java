package mainJava;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        ConsumDB ab = new ConsumDB();
        EquipmentDB abc = new EquipmentDB();
        EmployeeDB a = new EmployeeDB();

        a.createEmployeeDB();
        ab.createConsumDB();
        abc.createEquipmentDB();

        a.addToDB(1);
        abc.addToDB(1);
        ab.addToDB(1);

    }
}
