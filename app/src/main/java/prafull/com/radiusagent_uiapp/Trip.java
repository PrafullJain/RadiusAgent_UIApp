package prafull.com.radiusagent_uiapp;

public class Trip {
    private String from,to,costval,costsymb,tripdura;

    public Trip(String from, String to, String costval, String costsymb, String tripdura) {
        this.from = from;
        this.to = to;
        this.costval = costval;
        this.costsymb = costsymb;
        this.tripdura = tripdura;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", costval='" + costval + '\'' +
                ", costsymb='" + costsymb + '\'' +
                ", tripdura='" + tripdura + '\'' +
                '}';
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCostval() {
        return costval;
    }

    public void setCostval(String costval) {
        this.costval = costval;
    }

    public String getCostsymb() {
        return costsymb;
    }

    public void setCostsymb(String costsymb) {
        this.costsymb = costsymb;
    }

    public String getTripdura() {
        return tripdura;
    }

    public void setTripdura(String tripdura) {
        this.tripdura = tripdura;
    }


}
