import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static Statement stmt;

    public static void main(String[] args) {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sql", "postgres", "password");
            stmt = c.createStatement();
            //CreateTables();
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
        if (stmt == null) {
            throw new SQLException("Statement not initialized.");
        }

        String sqlCreateTableCompany = "CREATE TABLE company " +
                "(id SERIAL PRIMARY KEY NOT NULL, " +
                "name_company TEXT NOT NULL, " +
                "formation_date DATE NOT NULL, " +
                "inn BIGINT NOT NULL, " +
                "status BOOLEAN NOT NULL)";
        stmt.executeUpdate(sqlCreateTableCompany);

        String sqlCreateTableCar = "CREATE TABLE car " +
                "(id SERIAL PRIMARY KEY NOT NULL, " +
                "brand TEXT NOT NULL, " +
                "model TEXT NOT NULL, " +
                "vin BIGINT NOT NULL)";
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

    public static void DataOperations() {

    }

    public static void CreateData() throws SQLException {

    }

    public static void DelData() throws SQLException {

    }

    public static void AddData() throws SQLException {

    }

    public static void ChangeData() throws SQLException {

    }
}

