import controller.LoginController;
import dao.AccountDAO;
import dao.CustomerDAO;
import dao.DatabaseHelper;
import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import view.LoginView;

import java.util.Map;

public class Main extends Application {

    private static void seedSampleRecords(CustomerDAO customerDAO, AccountDAO accountDAO) {
        // 4 Customers + 6 Accounts = 10 Sample Records
        Customer c1 = new Customer("C001", "Mpho", "Ramagotsane", "Plot 11221, Kanye");
        Customer c2 = new Customer("C002", "Loago", "Mabeo", "Block 7, Gaborone");
        Customer c3 = new Customer("C003", "Rara", "Kgosiyame", "Plot 6524, Tlokweng");
        Customer c4 = new Customer("C004", "Daman", "Teto", "Francistown CBD");

        customerDAO.saveCustomer(c1);
        customerDAO.saveCustomer(c2);
        customerDAO.saveCustomer(c3);
        customerDAO.saveCustomer(c4);

        accountDAO.saveAccount(new SavingsAccount("SAV-1001", 1500.00, "Kanye", c1), c1);
        accountDAO.saveAccount(new InvestmentAccount("INV-1002", 2500.00, "Kanye", c1), c1);
        accountDAO.saveAccount(new ChequeAccount("CHQ-1003", 4200.00, "Gaborone Main", c1, "BAC", "Gaborone"), c1);
        accountDAO.saveAccount(new SavingsAccount("SAV-1004", 800.00, "Block 7", c2), c2);
        accountDAO.saveAccount(new InvestmentAccount("INV-1005", 1200.00, "Tlokweng", c3), c3);
        accountDAO.saveAccount(new ChequeAccount("CHQ-1006", 3100.00, "Francistown", c4, "Botswana Fibre", "Francistown"), c4);
    }

    @Override
    public void start(Stage primaryStage) {
        DatabaseHelper.initializeDatabase();
        CustomerDAO customerDAO = new CustomerDAO();
        AccountDAO accountDAO = new AccountDAO();

        seedSampleRecords(customerDAO, accountDAO);

        Map<String, Customer> customers = customerDAO.getAllCustomers();
        for (Customer c : customers.values()) {
            for (Account acc : accountDAO.getAccountsByCustomer(c)) {
                c.addAccount(acc);
            }
        }

        LoginView loginView = new LoginView();
        primaryStage.setTitle("Banking System - CSE202");
        primaryStage.setScene(loginView.createScene());
        new LoginController(loginView, primaryStage, customers);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}