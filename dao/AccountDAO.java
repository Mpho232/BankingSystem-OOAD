package dao;

import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public void saveAccount(Account account, Customer customer) {
        String sql = "INSERT OR REPLACE INTO accounts (account_number, customer_id, account_type, balance, branch, employer_company, company_address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account.getAccountNumber());
            pstmt.setString(2, customer.getCustomerId());
            pstmt.setString(3, account.getClass().getSimpleName());
            pstmt.setDouble(4, account.getBalance());
            pstmt.setString(5, account.getBranch());

            if (account instanceof ChequeAccount) {
                ChequeAccount ca = (ChequeAccount) account;
                pstmt.setString(6, ca.getEmployerCompany());
                pstmt.setString(7, ca.getCompanyAddress());
            } else {
                pstmt.setNull(6, Types.VARCHAR);
                pstmt.setNull(7, Types.VARCHAR);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBalance(String accountNumber, double newBalance, String txType, double amount) {
        String updateSql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        String logSql = "INSERT INTO transactions (account_number, type, amount) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection()) {
            try (PreparedStatement upStmt = conn.prepareStatement(updateSql)) {
                upStmt.setDouble(1, newBalance);
                upStmt.setString(2, accountNumber);
                upStmt.executeUpdate();
            }
            try (PreparedStatement logStmt = conn.prepareStatement(logSql)) {
                logStmt.setString(1, accountNumber);
                logStmt.setString(2, txType);
                logStmt.setDouble(3, amount);
                logStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Account> getAccountsByCustomer(Customer customer) {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE customer_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getCustomerId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String accNum = rs.getString("account_number");
                String type = rs.getString("account_type");
                double bal = rs.getDouble("balance");
                String branch = rs.getString("branch");

                Account acc = null;
                switch (type) {
                    case "SavingsAccount":
                        acc = new SavingsAccount(accNum, bal, branch, customer);
                        break;
                    case "InvestmentAccount":
                        acc = new InvestmentAccount(accNum, bal, branch, customer);
                        break;
                    case "ChequeAccount":
                        acc = new ChequeAccount(accNum, bal, branch, customer,
                                rs.getString("employer_company"), rs.getString("company_address"));
                        break;
                }
                if (acc != null) list.add(acc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}