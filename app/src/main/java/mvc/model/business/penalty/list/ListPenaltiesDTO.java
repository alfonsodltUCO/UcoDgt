package mvc.model.business.penalty.list;

import mvc.model.business.penalty.typeof;

public class ListPenaltiesDTO {
    private typeof reason;

    private Float maxQ,minQ;

    private Integer law,maxP,minP;

    private severityType type;

    public ListPenaltiesDTO(){

    }

    public ListPenaltiesDTO(typeof reason, Float maxQ, Float minQ, Integer law, Integer maxP, Integer minP, severityType type) {
        this.reason = reason;
        this.maxQ = maxQ;
        this.minQ = minQ;
        this.law = law;
        this.maxP = maxP;
        this.minP = minP;
        this.type = type;
    }

    public severityType getType() {
        return type;
    }

    public void setType(severityType type) {
        this.type = type;
    }

    public Integer getMaxP() {
        return maxP;
    }

    public void setMaxP(Integer maxP) {
        this.maxP = maxP;
    }

    public Integer getLaw() {
        return law;
    }

    public void setLaw(Integer law) {
        this.law = law;
    }

    public Integer getMinP() {
        return minP;
    }

    public void setMinP(Integer minP) {
        this.minP = minP;
    }

    public Float getMinQ() {
        return minQ;
    }

    public void setMinQ(Float minQ) {
        this.minQ = minQ;
    }

    public Float getMaxQ() {
        return maxQ;
    }

    public void setMaxQ(Float maxQ) {
        this.maxQ = maxQ;
    }

    public typeof getReason() {
        return reason;
    }

    public void setReason(typeof reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "ListPenaltiesDTO{" +
                "reason=" + reason +
                ", maxQ=" + maxQ +
                ", minQ=" + minQ +
                ", law=" + law +
                ", maxP=" + maxP +
                ", minP=" + minP +
                ", type=" + type +
                '}';
    }
}
