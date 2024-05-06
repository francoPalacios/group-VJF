package JVF.Finances;
import javafx.beans.property.*;

import java.time.LocalDate;

public class Budget {
    private SimpleIntegerProperty budgetId;
    private final int user_id;
    private final SimpleStringProperty budgetName;
    private SimpleDoubleProperty budgetincome;
    private final SimpleObjectProperty<LocalDate> budgetstartdate;
    private final SimpleObjectProperty<LocalDate> budgetenddate;
    public Budget(int userId, String budgetname, double budgetincome, LocalDate budgetstartdate, LocalDate budgetenddate) {
        this.user_id = userId;
        this.budgetId = new SimpleIntegerProperty();
        this.budgetName = new SimpleStringProperty(budgetname);
        this.budgetincome = new SimpleDoubleProperty(budgetincome);
        this.budgetstartdate = new SimpleObjectProperty<>(budgetstartdate);
        this.budgetenddate = new SimpleObjectProperty<>(budgetenddate);
    }

    public  int getuserID(){return user_id;}

    public String getBudgetName() {
        return budgetName.get();
    }

    public SimpleStringProperty budgetNameProperty() {
        return budgetName;
    }

    public double getBudgetincome() {
        return budgetincome.get();
    }

    public SimpleDoubleProperty budgetincomeProperty() {
        return budgetincome;
    }

    public void setBudgetincome(double budgetincome) {
        this.budgetincome.set(budgetincome);
    }

    public LocalDate getBudgetstartdate() {
        return budgetstartdate.get();
    }

    public SimpleObjectProperty<LocalDate> budgetstartdateProperty() {
        return budgetstartdate;
    }

    public LocalDate getBudgetenddate() {
        return budgetenddate.get();
    }

    public SimpleObjectProperty<LocalDate> budgetenddateProperty() {
        return budgetenddate;
    }

    public int getBudgetId() {
        return budgetId.get();
    }

    public SimpleIntegerProperty budgetIdProperty() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId.set(budgetId);
    }

    @Override
    public String toString() {
        return String.format("""
                        ID: %d,
                        Name: %s
                        """,
                this.getBudgetId(),
                this.getBudgetName());
    }
}
