package JVF.Finances;

public class FundingGroup {
    private String FGName;
    private String FGDes;

    public FundingGroup(String FGName, String FGDes) {
        this.FGName = FGName;
        this.FGDes = FGDes;
    }

    public String getFGName() { return FGName; }
    public String getFGDes() { return FGDes; }
}
