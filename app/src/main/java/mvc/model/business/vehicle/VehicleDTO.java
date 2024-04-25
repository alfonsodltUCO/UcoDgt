package mvc.model.business.vehicle;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;


/**
 * Represents a vehicle in the system.
 * This class encapsulates the data associated with a vehicle,
 * including its license plate, type, color, ITV validity period,
 * insurance ID, and insurance validity period.
 *
 * @author Alfonso de la torre
 */
public class VehicleDTO implements Serializable {

    private String licencePlate;

    private typeof carType;

    private typeofColor color;

    private Date validItvFrom;

    private int idInsurance;

    private Date validItvTo;

    /**
     * Default constructor for VehicleDTO.
     */

    public VehicleDTO(){

    }


    /**
     * Parameterized constructor for VehicleDTO.
     *
     * @param licencePlate The license plate of the vehicle.
     * @param typeCar The type of the car.
     * @param typeColor The color of the car.
     * @param validItvFrom The start date of the ITV validity period.
     * @param validItvTo The end date of the ITV validity period.
     * @param idInsurance The ID of the vehicle's insurance.
     */
    public VehicleDTO(String licencePlate,typeof typeCar,typeofColor typeColor,Date validItvFrom,Date validItvTo,int idInsurance){
        this.licencePlate=licencePlate;
        this.carType=typeCar;
        this.color=typeColor;
        this.validItvFrom=validItvFrom;
        this.idInsurance=idInsurance;
        this.validItvTo=validItvTo;
    }
    /**
     * Getter for the insurance ID of the vehicle.
     *
     * @return The insurance ID of the vehicle.
     */
    public int getIdInsurance() {
        return idInsurance;
    }

    /**
     * Setter for the insurance ID of the vehicle.
     *
     * @param id The insurance ID to set.
     */
    public void setIdInsurance(int id){
        this.idInsurance = id;
    }

    /**
     * Getter for the start date of the ITV validity period.
     *
     * @return The start date of the ITV validity period.
     */
    public Date getValidItvFrom() {
        return validItvFrom;
    }

    /**
     * Getter for the end date of the ITV validity period.
     *
     * @return The end date of the ITV validity period.
     */
    public Date getValidItvTo() {
        return validItvTo;
    }

    /**
     * Getter for the license plate of the vehicle.
     *
     * @return The license plate of the vehicle.
     */
    public String getLicencePlate() {
        return licencePlate;
    }

    /**
     * Getter for the type of the car.
     *
     * @return The type of the car.
     */
    public typeof getCarType() {
        return carType;
    }

    /**
     * Getter for the color of the car.
     *
     * @return The color of the car.
     */
    public typeofColor getColor() {
        return color;
    }

    /**
     * Setter for the type of the car.
     *
     * @param carType The type of the car to set.
     */
    public void setCarType(typeof carType) {
        this.carType = carType;
    }

    /**
     * Setter for the color of the car.
     *
     * @param color The color of the car to set.
     */
    public void setColor(typeofColor color) {
        this.color = color;
    }

    /**
     * Setter for the license plate of the vehicle.
     *
     * @param licencePlate The license plate to set.
     */
    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    /**
     * Setter for the start date of the ITV validity period.
     *
     * @param validItvFrom The start date of the ITV validity period to set.
     */
    public void setValidItvFrom(Date validItvFrom) {
        this.validItvFrom = validItvFrom;
    }

    /**
     * Setter for the end date of the ITV validity period.
     *
     * @param validItvTo The end date of the ITV validity period to set.
     */
    public void setValidItvTo(Date validItvTo) {
        this.validItvTo = validItvTo;
    }

    /**
     * Returns a string representation of the VehicleDTO object.
     *
     * @return A string representation of the VehicleDTO object.
     */
    @NonNull
    @Override
    public String toString() {
        return "VehicleDTO{" +
                "licencePlate='" + licencePlate + '\'' +
                ", carType=" + carType +
                ", color=" + color +
                ", validItvFrom=" + validItvFrom +
                ", validItvTo=" + validItvTo +
                '}';
    }
}
