package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
    private static final String URL = "jdbc:sqlite:banking.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        String createCustomers = "CREATE TABLE IF NOT EXISTS customers (" +
                "customer_id TEXT PRIMARY KEY, " +
                "first_name TEXT NOT NULL, " +
                "surname TEXT NOT NULL, " +
                "address TEXT NOT NULL);";

        String createAccounts = "CREATE TABLE IF NOT EXISTS accounts (" +
                "account_number TEXT PRIMARY KEY, " +
                "customer_id TEXT NOT NULL, " +
                "account_type TEXT NOT NULL, " +
                "balance REAL NOT NULL, " +
                "branch TEXT NOT NULL, " +
                "employer_company TEXT, " +
                "company_address TEXT, " +
                "FOREIGN KEY (customer_id) REFERENCES customers(customer_id));";

        String createTransactions = "CREATE TABLE IF NOT EXISTS transactions (" +
                "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "account_number TEXT NOT NULL, " +
                "type TEXT NOT NULL, " +
                "amount REAL NOT NULL, " +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (account_number) REFERENCES accounts(account_number));";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(createCustomers);
            stmt.execute(createAccounts);
            stmt.execute(createTransactions);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}