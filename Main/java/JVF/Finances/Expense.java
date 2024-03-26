package JVF.Finances;

public class Expense {

    private final int UserID;
    private final double amount;
    private final String recurrence;
    private final String budgetType;
    private final String description;

    public Expense(int UserID, double amount, String recurrence, String budgetType, String description) {
        this.UserID = UserID;
        this.amount = amount;
        this.recurrence = recurrence;
        this.budgetType = budgetType;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }
    public String getRecurrence() {
        return recurrence;
    }
    public String getBudgetType() {
        return budgetType;
    }
    public String getDescription() {
        return description;
    }
    public  int getuserID(){return UserID;}

    public int setBudgetID(String budgetType) {
        switch (budgetType) {
            case "Transportation":
                return 1;
            case "Housing":
                return 2;
            case "Food":
                return 3;
            case "Entertainment":
                return 4;
            case "Other":
                return 5;
            default:
                throw new IllegalArgumentException("Invalid budget type: " + budgetType);
        }
}   }