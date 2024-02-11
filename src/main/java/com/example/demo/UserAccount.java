package com.example.demo;

public class UserAccount {
    private String accountId;
    private double balance;
    private String name;

    public UserAccount(String accountId, double balance, String name) {
        this.accountId = accountId;
        this.balance = balance;
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}