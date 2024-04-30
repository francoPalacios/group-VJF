package JVF.Finances;

import java.time.LocalDate;
import java.util.Date;

public class Expense {

    private final int UserID;
    private final double amount;
    private final String Reciept;
    private final LocalDate ExpDate;
    private final int FGID;

    public Expense(int UserID, int FGid, double amount, String Receipt, LocalDate ExpDate) {
        this.UserID = UserID;
        this.amount = amount;
        this.Reciept = Receipt;
        this.ExpDate = ExpDate;
        this.FGID = FGid;
    }

    public double getAmount() {
        return amount;
    }
    public  int getuserID(){return UserID;}
    public String getReciept() { return Reciept; }
    public LocalDate getExpDate() { return ExpDate; }
    public int getFGID() { return FGID; }
}