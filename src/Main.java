import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {
    public static Statement stmt;
    static Scanner scan = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
            System.out.println("2 - удалить данные");
            System.out.println("3 - изменить данные");
            System.out.println("4 - просмотр таблицы");
            int num = scan.nextInt();
            if (num != 1 && num != 2 && num != 3 && num != 4) break;
            System.out.println("Выберите таблицу, в которой хотите внести изменения");

            String getTables = "SELECT table_name FROM information_schema.tables " +
                    "WHERE table_schema NOT IN ('information_schema','pg_catalog');";

            int pos = 1;
            List<String> tables = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery(getTables)) {
                while (rs.next()) {
                    tables.add(rs.getString("table_name"));
                    System.out.println(pos + " " + rs.getString("table_name"));
                    pos += 1;
                }
            }
            int curNum = scan.nextInt();
            String curTable;
            try {
                curTable = tables.get(curNum - 1);
            } catch (Exception e) {
                System.out.println("Нет такой таблицы");
                continue;
            }
            System.out.println("Выбрана таблица: " + curTable);
            //GetData(curTable);

            switch (num) {
                case 1: AddData(curTable); break;
                case 2: DelData(curTable); break;
                case 3: ChangeData(curTable); break;
                case 4: GetData(curTable); break;
            }
        }
    }

    public static void GetData (String curTable) throws SQLException {
        String getAddData = "SELECT * FROM " + curTable;
        ResultSet rs1 = stmt.executeQuery(getAddData);

        ResultSetMetaData metaData = rs1.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs1.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + ": " + rs1.getString(i) + "\t");
            }
            System.out.println();
        }
    }

    public static void AddData(String curTable) throws SQLException {
        //System.out.println("Введите sql запрос");
        //String sqlAdd = scan.next();
        String sqlAdd2 = "INSERT INTO car (brand, model, vin, status, company_id) VALUES " +
                "('volvo', 'xc90', '12345678901234567', true, 2)";
        stmt.executeUpdate(sqlAdd2);
    }

    public static void DelData(String curTable) throws SQLException {
        System.out.println("Введите id строки, которую хотите удалить");
        int curId = scan.nextInt();
        try {
            String sqlDel = "DELETE FROM " + curTable + " WHERE id = " + curId;
            stmt.executeUpdate(sqlDel);
            System.out.println("Удаление прошло успешно");
            //GetData(curTable);
        } catch (Exception e) {
            System.out.println("Ошибка");
        }
    }

    public static void ChangeData(String curTable) throws SQLException {
        System.out.println("Введите id строки, которую хотите изменить");
    }
}

/*
public static void AddData(String curTable) throws SQLException {
        String sqlColNames = "SELECT * FROM " + curTable + " LIMIT 1";
        ResultSet rs = stmt.executeQuery(sqlColNames);
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        List<String> colNames = new ArrayList<>();
        List<Object> newData = new ArrayList<>();

        for (int i = 1; i <= columnCount; i++) {
        colNames.add(metaData.getColumnName(i));
        }

        rs.close();

        try {
        for (int i = 1; i < colNames.size(); i++) {
        String colName = colNames.get(i);
        int colType = metaData.getColumnType(i + 1);

        System.out.println("Введите значение для столбца " + colName + ": ");

        switch (colType) {
        case Types.BIGINT:
        newData.add(scan.nextLong());
        break;
        case Types.INTEGER:
        newData.add(scan.nextInt());
        break;
        case Types.DOUBLE:
        case Types.FLOAT:
        newData.add(scan.nextDouble());
        break;
        case Types.DATE:
        String dateStr = scan.next();
        try {
        java.sql.Date sqlDate = new java.sql.Date(dateFormat.parse(dateStr).getTime());
        newData.add(sqlDate);
        } catch (ParseException e) {
        System.out.println("Неверный формат даты. Используйте формат YYYY-MM-DD.");
        return;
        }
        break;
        case Types.BOOLEAN:
        Boolean boolValue = null;
        while (boolValue == null) {
        System.out.println("Введите true или false для столбца " + colName + ": ");
        String boolStr = scan.next();
        if ("true".equalsIgnoreCase(boolStr)) {
        boolValue = true;
        } else if ("false".equalsIgnoreCase(boolStr)) {
        boolValue = false;
        } else {
        System.out.println("Ошибка: пожалуйста, введите 'true' или 'false'.");
        }
        }
        System.out.println("Добавлено логическое значение: " + boolValue);
        newData.add(boolValue);
        break;
default:
        newData.add(scan.next());
        break;
        }
        }
        } catch (Exception e) {
        System.out.println("Ошибка при вводе данных");
        return;
        }

        StringBuilder sqlAddData = new StringBuilder("INSERT INTO " + curTable + " (");
        for (int i = 1; i < colNames.size(); i++) {
        sqlAddData.append(colNames.get(i));
        if (i < colNames.size() - 1) {
        sqlAddData.append(", ");
        }
        }
        sqlAddData.append(") VALUES (");
        sqlAddData.append("?,".repeat(newData.size()));
        sqlAddData.setLength(sqlAddData.length() - 1);
        sqlAddData.append(")");

        try (PreparedStatement pstmt = stmt.getConnection().prepareStatement(sqlAddData.toString())) {
        for (int i = 0; i < newData.size(); i++) {
        Object value = newData.get(i);

        if (value instanceof Integer) {
        pstmt.setInt(i + 1, (Integer) value);
        } else if (value instanceof Long) {
        pstmt.setLong(i + 1, (Long) value);
        } else if (value instanceof Double) {
        pstmt.setDouble(i + 1, (Double) value);
        } else if (value instanceof java.sql.Date) {
        pstmt.setDate(i + 1, (java.sql.Date) value);
        } else if (value instanceof Boolean) {
        System.out.println("Передается логическое значение в SQL: " + value);
        pstmt.setBoolean(i + 1, (Boolean) value);
        } else {
        pstmt.setString(i + 1, value.toString());
        }
        }
        pstmt.executeUpdate();
        System.out.println("Данные успешно добавлены в таблицу " + curTable);
        } catch (SQLException e) {
        System.out.println("Ошибка при добавлении данных: " + e.getMessage());
        }
        }

 */