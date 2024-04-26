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
     * @param company   The company providing the insurance policy.
     */

    public InsurancePolicyDTO(Integer id, Float quantity,typeof company){
        this.company=company;
        this.id=id;
        this.quantity=quantity;
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
                ", company=" + company +
                '}';
    }
}
