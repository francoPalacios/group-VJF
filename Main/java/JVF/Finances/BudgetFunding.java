package JVF.Finances;

public class BudgetFunding {
    private int fundingGroup_id;
    private double amount;
    private int user_id;
    private int budget_id;


    public BudgetFunding(double amount, int userid, int budgetid, int fundingGroupid) {
        this.fundingGroup_id = fundingGroupid;
        this.amount = amount;
        this.user_id = userid;
        this.budget_id = budgetid;
    }

    public double getAmount() { return amount; }
    public int getUser_id() { return user_id; }
    public int getFundingGroup_id() { return fundingGroup_id; }
    public int getBudget_id() { return budget_id; }


}
