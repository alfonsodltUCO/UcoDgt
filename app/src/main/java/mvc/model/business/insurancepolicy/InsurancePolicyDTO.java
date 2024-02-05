package mvc.model.business.insurancepolicy;

import java.util.Date;

public class InsurancePolicyDTO {

    private Integer id;

    private Float quantity;

    private Date endDate;

    private Date startDate;

    private typeof company;

    public InsurancePolicyDTO(){

    }

    public InsurancePolicyDTO(Integer id, Float quantity, Date endDate, Date startDate,typeof company){
        this.company=company;
        this.endDate=endDate;
        this.id=id;
        this.quantity=quantity;
        this.startDate=startDate;
    }

    public Integer getId() {
        return id;
    }

    public Float getQuantity() {
        return quantity;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public typeof getCompany() {
        return company;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public void setCompany(typeof company) {
        this.company = company;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

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
