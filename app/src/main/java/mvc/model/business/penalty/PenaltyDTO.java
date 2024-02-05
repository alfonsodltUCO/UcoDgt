package mvc.model.business.penalty;

import java.util.Date;

public class PenaltyDTO {

    private Integer id;

    private Integer points;
    private Date date;

    private Float quantity;

    private typeof reason;

    private stateof state;

    public PenaltyDTO(){

    }

    public PenaltyDTO(Integer id,Integer points, Date date, Float quantity, typeof reason,stateof state){
        this.date=date;
        this.id=id;
        this.points=points;
        this.reason=reason;
        this.quantity=quantity;
        this.state=state;
    }

    public stateof getState() {
        return state;
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Float getQuantity() {
        return quantity;
    }

    public Integer getPoints() {
        return points;
    }

    public typeof getReason() {
        return reason;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public void setReason(typeof reason) {
        this.reason = reason;
    }

    public void setState(stateof state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "PenaltyDTO{" +
                "id=" + id +
                ", points=" + points +
                ", date=" + date +
                ", quantity=" + quantity +
                ", reason=" + reason +
                ", state=" + state +
                '}';
    }
}
