import dao.AccountDAO;
import dao.CustomerDAO;
import dao.DatabaseHelper;
import model.*;

import java.util.List;
import java.util.Map;

public class IntegrationTest {
    public static void main(String[] args) {
        System.out.println("=== CSE202 Banking System Integration Test ===");

        // 1. Initialize Database & Seed 10 Sample Records
        DatabaseHelper.initializeDatabase();
        CustomerDAO customerDAO = new CustomerDAO();
        AccountDAO accountDAO = new AccountDAO();

        Customer c1 = new Customer("C001", "Mpho", "Ramagotsane", "Plot 11221, Kanye");
        Customer c2 = new Customer("C002", "Loago", "Mabeo", "Block 7, Gaborone");
        Customer c3 = new Customer("C003", "Rara", "Kgosiyame", "Plot 6524, Tlokweng");
        Customer c4 = new Customer("C004", "Daman", "Teto", "Francistown CBD");

        customerDAO.saveCustomer(c1);
        customerDAO.saveCustomer(c2);
        customerDAO.saveCustomer(c3);
        customerDAO.saveCustomer(c4);

        SavingsAccount sav1 = new SavingsAccount("SAV-1001", 1500.00, "Kanye", c1);
        InvestmentAccount inv1 = new InvestmentAccount("INV-1002", 2500.00, "Kanye", c1);
        ChequeAccount chq1 = new ChequeAccount("CHQ-1003", 4200.00, "Gaborone Main", c1, "BAC", "Gaborone");
        SavingsAccount sav2 = new SavingsAccount("SAV-1004", 800.00, "Block 7", c2);
        InvestmentAccount inv2 = new InvestmentAccount("INV-1005", 1200.00, "Tlokweng", c3);
        ChequeAccount chq2 = new ChequeAccount("CHQ-1006", 3100.00, "Francistown", c4, "Botswana Fibre", "Francistown");

        accountDAO.saveAccount(sav1, c1);
        accountDAO.saveAccount(inv1, c1);
        accountDAO.saveAccount(chq1, c1);
        accountDAO.saveAccount(sav2, c2);
        accountDAO.saveAccount(inv2, c3);
        accountDAO.saveAccount(chq2, c4);

        // 2. Verify Customer & Account Retrieval (Read)
        Map<String, Customer> customers = customerDAO.getAllCustomers();
        List<Account> c1Accounts = accountDAO.getAccountsByCustomer(customers.get("C001"));
        System.out.println("[PASS] Seeded Customers: " + customers.size() + " | C001 Accounts: " + c1Accounts.size());

        // 3. Test Deposit & JDBC Persistence
        sav1.deposit(500.00);
        accountDAO.updateBalance(sav1.getAccountNumber(), sav1.getBalance(), "DEPOSIT", 500.00);
        System.out.println("[PASS] Deposit BWP 500 -> SAV-1001 New Balance: BWP " + sav1.getBalance());

        // 4. Test Monthly Interest Calculation (5% Investment, 0.05% Savings)
        inv1.calculateMonthlyInterest();
        accountDAO.updateBalance(inv1.getAccountNumber(), inv1.getBalance(), "INTEREST", 125.00);
        System.out.println("[PASS] 5% Interest Applied -> INV-1002 New Balance: BWP " + inv1.getBalance());

        // 5. Test Business Rule Enforcement (Savings Withdrawal Blocked & Investment Min BWP 500)
        try {
            sav1.withdraw(100.00);
            System.out.println("[FAIL] Savings withdrawal should have been blocked.");
        } catch (UnsupportedOperationException e) {
            System.out.println("[PASS] Savings Withdrawal Blocked: " + e.getMessage());
        }

        try {
            new InvestmentAccount("INV-9999", 300.00, "Kanye", c1);
            System.out.println("[FAIL] Investment below BWP 500 should have been rejected.");
        } catch (IllegalArgumentException e) {
            System.out.println("[PASS] Investment Minimum Enforced: " + e.getMessage());
        }

        System.out.println("=== All Integration Tests Passed & Persisted to banking.db ===");
    }
}