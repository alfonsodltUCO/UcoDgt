package mvc.model.business.penalty;

import java.io.Serializable;
import java.util.Date;

/**
 * This class represents a penalty data transfer object (DTO).
 * It encapsulates information about a penalty, including its unique identifier, points, date, quantity, reason, state,
 * client's DNI, worker's DNI, description, place, informed status, locality, and license plate.
 * @author Alfonso de la torre
 */

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



    /**
     * Constructs a new PenaltyDTO object.
     */
    public PenaltyDTO(){

    }

    /**
     * Constructs a new PenaltyDTO object with the specified attributes.
     *
     * @param id               The unique identifier of the penalty.
     * @param points           The points of the penalty.
     * @param date             The date of the penalty.
     * @param quantity         The quantity of the penalty.
     * @param reason           The reason for the penalty.
     * @param state            The state of the penalty.
     * @param dniClient        The client's DNI associated with the penalty.
     * @param dniWorker        The worker's DNI associated with the penalty.
     * @param description      The description of the penalty.
     * @param place            The place associated with the penalty.
     * @param informedAtTheMoment True if the penalty is informed; otherwise, false.
     * @param locality         The locality associated with the penalty.
     * @param licenceplate     The license plate associated with the penalty.
     */

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

    /**
     * Retrieves the state of the penalty.
     *
     * @return The state of the penalty.
     */

    public stateof getState() {
        return state;
    }

    /**
     * Retrieves the unique identifier of the penalty.
     *
     * @return The unique identifier of the penalty.
     */


    public Integer getId() {
        return id;
    }

    /**
     * Retrieves the date of the penalty.
     *
     * @return The date of the penalty.
     */


    public Date getDate() {
        return date;
    }

    /**
     * Retrieves the quantity of the penalty.
     *
     * @return The quantity of the penalty.
     */


    public Float getQuantity() {
        return quantity;
    }

    /**
     * Retrieves the points of the penalty.
     *
     * @return The points of the penalty.
     */


    public Integer getPoints() {
        return points;
    }

    /**
     * Retrieves the reason for the penalty.
     *
     * @return The reason for the penalty.
     */


    public typeof getReason() {
        return reason;
    }

    /**
     * Sets the unique identifier of the penalty.
     *
     * @param id The unique identifier to set.
     */


    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Sets the date of the penalty.
     *
     * @param date The date to set.
     */


    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Sets the points of the penalty.
     *
     * @param points The points to set.
     */


    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * Sets the quantity of the penalty.
     *
     * @param quantity The quantity to set.
     */


    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    /**
     * Sets the reason for the penalty.
     *
     * @param reason The reason to set.
     */


    public void setReason(typeof reason) {
        this.reason = reason;
    }

    /**
     * Sets the state of the penalty.
     *
     * @param state The state to set.
     */

    public void setState(stateof state) {
        this.state = state;
    }


    /**
     * Returns a string representation of the PenaltyDTO object.
     *
     * @return A string representation of the PenaltyDTO object, including its attributes.
     */
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

    /**
     * Retrieves the license plate associated with the penalty.
     *
     * @return The license plate associated with the penalty.
     */

    public String getLicenceplate() {
        return licenceplate;
    }


    /**
     * Sets the license plate associated with the penalty.
     *
     * @param licenceplate The license plate to set.
     */


    public void setLicenceplate(String licenceplate) {
        this.licenceplate = licenceplate;
    }

    /**
     * Retrieves the locality associated with the penalty.
     *
     * @return  locality The  locality associated with the penalty.
     */

    public String getLocality() {
        return locality;
    }

    /**
     * Sets the locality associated with the penalty.
     *
     * @param locality The locality to set.
     */


    public void setLocality(String locality) {
        this.locality = locality;
    }

    /**
     * Retrieves the informed status of the penalty.
     *
     * @return True if the penalty is informed; otherwise, false.
     */


    public boolean isInformedAtTheMoment() {
        return informedAtTheMoment;
    }
    /**
     * Sets the informed status of the penalty.
     *
     * @param informedAtTheMoment The informed status to set.
     */

    public void setInformedAtTheMoment(boolean informedAtTheMoment) {
        this.informedAtTheMoment = informedAtTheMoment;
    }


    /**
     * Retrieves the place associated with the penalty.
     *
     * @return The place associated with the penalty.
     */

    public String getPlace() {
        return place;
    }

    /**
     * Sets the place associated with the penalty.
     *
     * @param place The place to set.
     */

    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * Retrieves the description of the penalty.
     *
     * @return The description of the penalty.
     */

    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the penalty.
     *
     * @param description The description to set.
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the worker's DNI associated with the penalty.
     *
     * @return The worker's DNI associated with the penalty.
     */

    public String getDniWorker() {
        return dniWorker;
    }

    /**
     * Sets the worker's DNI associated with the penalty.
     *
     * @param dniWorker The worker's DNI to set.
     */

    public void setDniWorker(String dniWorker) {
        this.dniWorker = dniWorker;
    }

    /**
     * Retrieves the client's DNI associated with the penalty.
     *
     * @return The client's DNI associated with the penalty.
     */

    public String getDniClient() {
        return dniClient;
    }

    /**
     * Sets the client's DNI associated with the penalty.
     *
     * @param dniClient The client's DNI to set.
     */

    public void setDniClient(String dniClient) {
        this.dniClient = dniClient;
    }
}
