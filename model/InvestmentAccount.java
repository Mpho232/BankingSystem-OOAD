package model;

public class InvestmentAccount extends Account {

    public InvestmentAccount(String accountNumber, double initialBalance, String branch, Customer holder) {
        super(accountNumber, initialBalance, branch, holder);
        if (initialBalance < 500.00) {
            throw new IllegalArgumentException("Investment account requires an initial deposit of at least BWP 500.00.");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Invalid withdrawal amount or insufficient funds.");
        }
    }

    @Override
    public void calculateMonthlyInterest() {
        double interest = balance * 0.05; // 5%
        balance += interest;
    }
}