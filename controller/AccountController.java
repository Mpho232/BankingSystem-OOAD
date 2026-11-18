package controller;

import model.*;
import view.AccountView;

public class AccountController {
    private AccountView view;
    private Customer customer;

    public AccountController(AccountView view, Customer customer) {
        this.view = view;
        this.customer = customer;
        view.getCustomerInfoLabel().setText("Welcome, " + customer.getFirstName() + " " + customer.getSurname() + " (" + customer.getCustomerId() + ")");
        attachEvents();
        refreshSummary();
    }

    private void attachEvents() {
        view.getOpenAccountButton().setOnAction(e -> handleOpenAccount());
        view.getDepositButton().setOnAction(e -> handleDeposit());
        view.getWithdrawButton().setOnAction(e -> handleWithdraw());
        view.getApplyInterestButton().setOnAction(e -> handlePayInterest());
    }

    private void handleOpenAccount() {
        String type = view.getAccountTypeBox().getValue();
        if (type == null) {
            showMessage("Please select an account type first.");
            return;
        }

        try {
            double initialDeposit = Double.parseDouble(view.getAmountField().getText().trim());
            String accNum = type.substring(0, 3).toUpperCase() + "-" + (1000 + customer.getAccounts().size() + 1);
            Account newAccount = null;

            switch (type) {
                case "Savings":
                    newAccount = new SavingsAccount(accNum, initialDeposit, "Gaborone Main", customer);
                    break;
                case "Investment":
                    newAccount = new InvestmentAccount(accNum, initialDeposit, "Gaborone Main", customer);
                    break;
                case "Cheque":
                    String company = view.getEmployerField().getText().trim();
                    String address = view.getEmployerAddressField().getText().trim();
                    if (company.isEmpty() || address.isEmpty()) {
                        showMessage("Cheque account requires employer name and address.");
                        return;
                    }
                    newAccount = new ChequeAccount(accNum, initialDeposit, "Gaborone Main", customer, company, address);
                    break;
            }

            customer.addAccount(newAccount);
            refreshSummary();
            showMessage("Opened " + type + " account (" + accNum + ") successfully.");
        } catch (NumberFormatException ex) {
            showMessage("Enter a valid numeric amount.");
        } catch (IllegalArgumentException ex) {
            showMessage("Error: " + ex.getMessage());
        }
    }

    private void handleDeposit() {
        Account target = findAccountBySelectedType();
        if (target == null) return;

        try {
            double amount = Double.parseDouble(view.getAmountField().getText().trim());
            if (amount <= 0) {
                showMessage("Deposit amount must be greater than 0.");
                return;
            }
            target.deposit(amount);
            refreshSummary();
            showMessage("Deposited BWP " + amount + " into " + target.getAccountNumber());
        } catch (NumberFormatException ex) {
            showMessage("Enter a valid numeric amount.");
        }
    }

    private void handleWithdraw() {
        Account target = findAccountBySelectedType();
        if (target == null) return;

        try {
            double amount = Double.parseDouble(view.getAmountField().getText().trim());
            target.withdraw(amount);
            refreshSummary();
            showMessage("Withdrew BWP " + amount + " from " + target.getAccountNumber());
        } catch (NumberFormatException ex) {
            showMessage("Enter a valid numeric amount.");
        } catch (UnsupportedOperationException | IllegalArgumentException ex) {
            showMessage("Blocked: " + ex.getMessage());
        }
    }

    private void handlePayInterest() {
        for (Account acc : customer.getAccounts()) {
            acc.calculateMonthlyInterest();
        }
        refreshSummary();
        showMessage("Monthly interest applied to all eligible accounts.");
    }

    private Account findAccountBySelectedType() {
        String type = view.getAccountTypeBox().getValue();
        if (type == null) {
            showMessage("Select an account type first.");
            return null;
        }
        for (Account acc : customer.getAccounts()) {
            if (type.equals("Savings") && acc instanceof SavingsAccount) return acc;
            if (type.equals("Investment") && acc instanceof InvestmentAccount) return acc;
            if (type.equals("Cheque") && acc instanceof ChequeAccount) return acc;
        }
        showMessage("No " + type + " account found for this customer. Open one first.");
        return null;
    }

    private void refreshSummary() {
        StringBuilder sb = new StringBuilder();
        for (Account acc : customer.getAccounts()) {
            sb.append(acc.getClass().getSimpleName())
              .append(" [").append(acc.getAccountNumber()).append("] - Balance: BWP ")
              .append(String.format("%.2f", acc.getBalance())).append("\n");
        }
        view.getAccountDisplayArea().setText(sb.toString());
    }

    private void showMessage(String msg) {
        view.getAccountDisplayArea().appendText("\n> " + msg);
    }
}