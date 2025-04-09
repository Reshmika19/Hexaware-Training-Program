package entity;

import java.sql.Date;

public class Expense {
    private int expenseId;
    private int userId;
    private int categoryId;
    private double amount;
    private Date date;
    private String description;

    public Expense() {
    }

    public Expense(int userId, int categoryId, double amount, Date date, String note) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
        this.description = note;
    }

    public Expense(int expenseId, int userId, int categoryId, double amount, Date date, String description) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ExpenseId : " + expenseId +
                ", UserId : " + userId +
                ", CategoryId : " + categoryId +
                ", Amount : " + amount +
                ", Date : " + date +
                ", Description : '" + description + '\'';
    }
}
