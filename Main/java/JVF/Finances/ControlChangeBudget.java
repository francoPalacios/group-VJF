package JVF.Finances;

public interface ControlChangeBudget {
    void registerForBudgetChange(ExpenseBudgetObserver ebo);
    void registerForCashChange(ExpenseCashObserver eco);

    void notifyBudgetChange();
    void notifyCashChange();
}
