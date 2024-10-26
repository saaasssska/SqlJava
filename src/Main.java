import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static Statement stmt;
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sql", "postgres", "password");
            stmt = c.createStatement();
            //CreateTables();
            //CreateData();
            DataOperations();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (c != null) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void CreateTables() throws SQLException {
        String sqlCreateTableCompany = "CREATE TABLE company " +
                "(id SERIAL PRIMARY KEY NOT NULL, " +
                "name_company TEXT NOT NULL, " +
                "formation_date DATE NOT NULL, " +
                "inn BIGINT NOT NULL)";
        stmt.executeUpdate(sqlCreateTableCompany);

        String sqlCreateTableCar = "CREATE TABLE car " +
                "(id SERIAL PRIMARY KEY NOT NULL, " +
                "brand TEXT NOT NULL, " +
                "model TEXT NOT NULL, " +
                "vin BIGINT NOT NULL, " +
                "status BOOLEAN NOT NULL)";
        stmt.executeUpdate(sqlCreateTableCar);

        String sqlConnect1 = "ALTER TABLE car " +
                "ADD company_id INT, " +
                "ADD FOREIGN KEY (company_id) REFERENCES company(id)";
        stmt.executeUpdate(sqlConnect1);

        String sqlCreateTableUsers = "CREATE TABLE users " +
                "(id SERIAL PRIMARY KEY NOT NULL, " +
                "first_name TEXT NOT NULL, " +
                "last_name TEXT NOT NULL, " +
                "num_driver_license BIGINT NOT NULL)";
        stmt.executeUpdate(sqlCreateTableUsers);

        String sqlCreateTableLease = "CREATE TABLE lease " +
                "(id SERIAL PRIMARY KEY NOT NULL, " +
                "start_date DATE NOT NULL, " +
                "end_date DATE NOT NULL)";
        stmt.executeUpdate(sqlCreateTableLease);

        String sqlConnect2 = "ALTER TABLE lease " +
                "ADD users_id INT, " +
                "ADD car_id INT, " +
                "ADD FOREIGN KEY (users_id) REFERENCES users(id), " +
                "ADD FOREIGN KEY (car_id) REFERENCES car(id)";
        stmt.executeUpdate(sqlConnect2);

        String sqlCreateTableCompanyPayments = "CREATE TABLE payments " +
                "(id SERIAL PRIMARY KEY NOT NULL, " +
                "date_pay DATE NOT NULL, " +
                "amount_pay INT NOT NULL)";
        stmt.executeUpdate(sqlCreateTableCompanyPayments);

        String sqlConnection3 = "ALTER TABLE payments " +
                "ADD lease_id INT, " +
                "ADD FOREIGN KEY(lease_id) REFERENCES lease(id)";
        stmt.executeUpdate(sqlConnection3);
    }

    public static void CreateData() throws SQLException {
        String sqlDataCompany = "INSERT INTO company (name_company, formation_date, inn) VALUES " +
                "('BelkaCar', '2010-06-15', 123456789012), " +
                "('CityDrive', '2015-03-20', 987654321098), " +
                "('Delimobil', '2018-11-11', 112233445566)";
        stmt.executeUpdate(sqlDataCompany);

        String sqlDataCar = "INSERT INTO car (brand, model, vin, status, company_id) VALUES " +
                "('Tesla', 'Model S', 11111111111111111, TRUE, 1), " +
                "('Ford', 'Mondeo', 22222222222222222, TRUE, 2), " +
                "('Toyota', 'Corolla', 33333333333333333, FALSE, 1), " +
                "('Nissan', 'Leaf', 44444444444444444, TRUE, 3), " +
                "('Nissan', 'Teana', 55555555555555555, TRUE, 3), " +
                "('Haval', 'Jolion', 66666666666666666, TRUE, 1), " +
                "('Chery', 'Tiggo 4 pro', 77777777777777777, FALSE, 3), " +
                "('Kia', 'Rio', 88888888888888888, TRUE, 2), " +
                "('Mazda', '6', 99999999999999999, FALSE, 2)";
        stmt.executeUpdate(sqlDataCar);

        String sqlDataUsers = "INSERT INTO users (first_name, last_name, num_driver_license) VALUES " +
                "('Dmitry', 'Popov', 123456789), " +
                "('Kirill', 'Ivanov', 987654321), " +
                "('Arseniy', 'Egorov', 456789123), " +
                "('Arsen', 'Volkov', 984394209)";
        stmt.executeUpdate(sqlDataUsers);

        String sqlDataLease = "INSERT INTO lease (start_date, end_date, users_id, car_id) VALUES " +
                "('2024-01-01', '2024-01-15', 1, 1), " +
                "('2024-02-01', '2024-02-15', 2, 2), " +
                "('2024-03-01', '2024-03-15', 3, 4)";
        stmt.executeUpdate(sqlDataLease);

        String sqlDataPayments = "INSERT INTO payments (date_pay, amount_pay, lease_id) VALUES " +
                "('2024-01-10', 800, 1), " +
                "('2024-02-10', 900, 2), " +
                "('2024-03-10', 750, 3)";
        stmt.executeUpdate(sqlDataPayments);
    }

    public static void DataOperations() throws SQLException {
        while (true) {
            System.out.println("Изменение данных базы данных");
            System.out.println("1 - добавить данные");
            System.out.println("2 - удалить удалить");
            System.out.println("3 - изменить данные");
            int num = scan.nextInt();
            if (num != 1 && num != 2 && num != 3) break;
            switch (num) {
                case 1: AddData(); break;
                case 2: DelData(); break;
                case 3: ChangeData(); break;
            }
        }
    }

    public static void AddData() throws SQLException {

    }

    public static void DelData() throws SQLException {

    }

    public static void ChangeData() throws SQLException {

    }
}

