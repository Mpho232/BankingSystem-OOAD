package model;

public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected String branch;
    protected Customer holder;

    public Account(String accountNumber, double initialBalance, String branch, Customer holder) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.branch = branch;
        this.holder = holder;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public abstract void withdraw(double amount) throws IllegalArgumentException;
    public abstract void calculateMonthlyInterest();

    public double getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
    public String getBranch() { return branch; }
}