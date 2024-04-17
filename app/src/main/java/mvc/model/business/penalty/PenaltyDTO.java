package mvc.model.business.penalty;

import java.io.Serializable;
import java.util.Date;

public class PenaltyDTO implements Serializable {

    private Integer id;

    private Integer points;
    private Date date;


    private Float quantity;

    private typeof reason;

    private stateof state;

    private  String dniClient;

    private String dniWorker;

    private String description;

    private String place;

    private boolean informedAtTheMoment;

    private String locality;

    private String licenceplate;


    public PenaltyDTO(){

    }

    public PenaltyDTO(Integer id, Integer points, Date date, Float quantity, typeof reason, stateof state, String dniClient, String dniWorker, String description, String place, boolean informedAtTheMoment, String locality, String licenceplate){
        this.date=date;
        this.id=id;
        this.points=points;
        this.reason=reason;
        this.quantity=quantity;
        this.state=state;
        this.dniClient = dniClient;
        this.dniWorker = dniWorker;
        this.description = description;
        this.place = place;
        this.informedAtTheMoment = informedAtTheMoment;
        this.locality = locality;
        this.licenceplate = licenceplate;
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
                ", dniClient='" + dniClient + '\'' +
                ", dniWorker='" + dniWorker + '\'' +
                ", description='" + description + '\'' +
                ", place='" + place + '\'' +
                ", informedAtTheMoment=" + informedAtTheMoment +
                ", locality='" + locality + '\'' +
                ", licenceplate='" + licenceplate + '\'' +
                '}';
    }

    public String getLicenceplate() {
        return licenceplate;
    }

    public void setLicenceplate(String licenceplate) {
        this.licenceplate = licenceplate;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public boolean isInformedAtTheMoment() {
        return informedAtTheMoment;
    }

    public void setInformedAtTheMoment(boolean informedAtTheMoment) {
        this.informedAtTheMoment = informedAtTheMoment;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDniWorker() {
        return dniWorker;
    }

    public void setDniWorker(String dniWorker) {
        this.dniWorker = dniWorker;
    }

    public String getDniClient() {
        return dniClient;
    }

    public void setDniClient(String dniClient) {
        this.dniClient = dniClient;
    }
}
