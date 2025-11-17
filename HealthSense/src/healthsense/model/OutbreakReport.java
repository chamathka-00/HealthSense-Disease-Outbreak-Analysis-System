package healthsense.model;

public class OutbreakReport {

    private String region;
    private String disease;
    private int cases;
    private boolean processed =  false;

    public OutbreakReport(String region, String disease, int cases) { // Constructor to create a new outbreak report
        this.region = region;
        this.disease = disease;
        this.cases = cases;
    }

    public String getRegion() {
        return region;
    }

    public String getDisease() {
        return disease;
    }

    public int getCases() {
        return cases;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    @Override
    public String toString() { // Returns outbreak details
        return "Region: " + region + ", Disease: " + disease + ", Cases: " + cases;
    }
}
