package org;
public class Bank {
    private int account;
    private String name;
    private String type;
    private String branch;
    private int amount;

    public Bank(int account, String name, String type, String branch, int amount) {
        this.account = account;
        this.name = name;
        this.type = type;
        this.branch = branch;
        this.amount = amount;
    }

    public int getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getBranch() {
        return branch;
    }

    public int getAmount() {
        return amount;
    }
    
}
