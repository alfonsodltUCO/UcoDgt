package mvc.model.business.insurancepolicy;

import androidx.annotation.NonNull;

import java.util.Date;

/**
 * This class represents a data transfer object (DTO) for an insurance policy.
 * It stores information such as the policy ID, quantity, start date, end date, and company.
 * @author Alfonso de la torre
 */

public class InsurancePolicyDTO {

    private Integer id;

    private Float quantity;

    private Date endDate;

    private Date startDate;

    private typeof company;

    /**
     * Constructs a new InsurancePolicyDTO object with default values.
     */

    public InsurancePolicyDTO(){

    }

    /**
     * Constructs a new InsurancePolicyDTO object with the specified attributes.
     *
     * @param id        The ID of the insurance policy.
     * @param quantity  The quantity of the insurance policy.
     * @param endDate   The end date of the insurance policy.
     * @param startDate The start date of the insurance policy.
     * @param company   The company providing the insurance policy.
     */

    public InsurancePolicyDTO(Integer id, Float quantity, Date endDate, Date startDate,typeof company){
        this.company=company;
        this.endDate=endDate;
        this.id=id;
        this.quantity=quantity;
        this.startDate=startDate;
    }

    /**
     * Gets the ID of the insurance policy.
     *
     * @return The ID of the insurance policy.
     */

    public Integer getId() {
        return id;
    }

    /**
     * Gets the quantity of the insurance policy.
     *
     * @return The quantity of the insurance policy.
     */

    public Float getQuantity() {
        return quantity;
    }

    /**
     * Gets the end date of the insurance policy.
     *
     * @return The end date of the insurance policy.
     */

    public Date getEndDate() {
        return endDate;
    }

    /**
     * Gets the start date of the insurance policy.
     *
     * @return The start date of the insurance policy.
     */

    public Date getStartDate() {
        return startDate;
    }

    /**
     * Gets the company providing the insurance policy.
     *
     * @return The company providing the insurance policy.
     */

    public typeof getCompany() {
        return company;
    }

    /**
     * Sets the ID of the insurance policy.
     *
     * @param id The ID of the insurance policy.
     */


    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Sets the quantity of the insurance policy.
     *
     * @param quantity The quantity of the insurance policy.
     */


    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    /**
     * Sets the company providing the insurance policy.
     *
     * @param company The company providing the insurance policy.
     */


    public void setCompany(typeof company) {
        this.company = company;
    }

    /**
     * Sets the end date of the insurance policy.
     *
     * @param endDate The end date of the insurance policy.
     */


    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Sets the start date of the insurance policy.
     *
     * @param startDate The start date of the insurance policy.
     */

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns a string representation of the InsurancePolicyDTO object.
     *
     * @return A string representation of the InsurancePolicyDTO object.
     */

    @NonNull
    @Override
    public String toString() {
        return "InsurancePolicyDTO{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", endDate=" + endDate +
                ", startDate=" + startDate +
                ", company=" + company +
                '}';
    }
}
