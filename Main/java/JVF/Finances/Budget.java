package JVF.Finances;
import java.time.LocalDate;

public class Budget {
    private final int user_id;
    private final String budgetname;
    private double budgetincome;
    private final LocalDate budgetstartdate;
    private final LocalDate budgetenddate;
    public Budget(int userId, String budgetname, double budgetincome, LocalDate budgetstartdate, LocalDate budgetenddate) {
        this.user_id = userId;
        this.budgetname = budgetname;
        this.budgetincome = budgetincome;
        this.budgetstartdate = budgetstartdate;
        this.budgetenddate = budgetenddate;
    }
    public  int getuserID(){return user_id;}
    public String getbudgetname() {
        return budgetname;
    }
    public void setbudgetincome(double budgetincome) {
        this.budgetincome = budgetincome;
    }
    public double getbudgetincome() {
        return budgetincome;
    }
    public LocalDate getbudgetstartdate() { return budgetstartdate; }
    public LocalDate getbudgetenddate() { return budgetenddate; }


}
