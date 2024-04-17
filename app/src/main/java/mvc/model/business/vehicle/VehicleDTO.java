package mvc.model.business.vehicle;
import java.io.Serializable;
import java.util.Date;

public class VehicleDTO implements Serializable {

    private String licencePlate;

    private typeof carType;

    private typeofColor color;

    private Date validItvFrom;

    private int idInsurance;

    private Date validItvTo;

    public VehicleDTO(){

    }

    public VehicleDTO(String licencePlate,typeof typeCar,typeofColor typeColor,Date validItvFrom,Date validItvTo,int idInsurance){
        this.licencePlate=licencePlate;
        this.carType=typeCar;
        this.color=typeColor;
        this.validItvFrom=validItvFrom;
        this.idInsurance=idInsurance;
        this.validItvTo=validItvTo;
    }


    public int getIdInsurance() {
        return idInsurance;
    }

    public void setIdInsurance(int id){
        this.idInsurance=id;
    }

    public Date getValidItvFrom() {
        return validItvFrom;
    }

    public Date getValidItvTo() {
        return validItvTo;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public typeof getCarType() {
        return carType;
    }

    public typeofColor getColor() {
        return color;
    }

    public void setCarType(typeof carType) {
        this.carType = carType;
    }

    public void setColor(typeofColor color) {
        this.color = color;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public void setValidItvFrom(Date validItvFrom) {
        this.validItvFrom = validItvFrom;
    }

    public void setValidItvTo(Date validItvTo) {
        this.validItvTo = validItvTo;
    }

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
