package JVF.Finances;
import java.time.LocalDate;

public class Budget {
    private final int user_id;
    private final String recurrence;
    private final String budgetType;
    private final double budgetincome;
    private final LocalDate budgetstartdate;
    private final LocalDate budgetenddate;
    public Budget(int userId, String budgetType, double budgetincome, LocalDate budgetstartdate, LocalDate budgetenddate, String recurrence) {
        this.recurrence = recurrence;
        this.user_id = userId;
        this.budgetType = budgetType;
        this.budgetincome = budgetincome;
        this.budgetstartdate = budgetstartdate;
        this.budgetenddate = budgetenddate;
    }
    public  int getuserID(){return user_id;}
    public String getBudgetType() {
        return budgetType;
    }
    public double getbudgetincome() {
        return budgetincome;
    }
    public LocalDate getbudgetstartdate() { return budgetstartdate; }
    public LocalDate getbudgetenddate() { return budgetenddate; }
    public String getRecurrence() { return recurrence; }
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
    }
}
