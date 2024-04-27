package com.example.ucodgt.mvc.model.business.penalty.list;

import androidx.annotation.NonNull;

import com.example.ucodgt.mvc.model.business.penalty.typeof;

/**
 * This class represents a data transfer object (DTO) for a list of penalties.
 * It stores information such as the reason for the penalty, maximum and minimum quantities,
 * the law associated with the penalty, maximum and minimum points, and the severity type.
 * @author Alfonso de la torre
 */

public class ListPenaltyDTO {
    private typeof reason;

    private Float maxQ,minQ;

    private Integer law,maxP,minP;

    private severityType type;

    /**
     * Constructs a new ListPenaltyDTO object with default values.
     */

    public ListPenaltyDTO(){

    }

    /**
     * Constructs a new ListPenaltyDTO object with the specified attributes.
     *
     * @param reason The reason for the penalty.
     * @param maxQ   The maximum quantity associated with the penalty.
     * @param minQ   The minimum quantity associated with the penalty.
     * @param law    The law associated with the penalty.
     * @param maxP   The maximum points associated with the penalty.
     * @param minP   The minimum points associated with the penalty.
     * @param type   The severity type of the penalty.
     */


    public ListPenaltyDTO(typeof reason, Float maxQ, Float minQ, Integer law, Integer maxP, Integer minP, severityType type) {
        this.reason = reason;
        this.maxQ = maxQ;
        this.minQ = minQ;
        this.law = law;
        this.maxP = maxP;
        this.minP = minP;
        this.type = type;
    }

    /**
     * Gets the severity type of the penalty.
     *
     * @return The severity type of the penalty.
     */


    public severityType getType() {
        return type;
    }

    /**
     * Sets the severity type of the penalty.
     *
     * @param type The severity type of the penalty.
     */



    public void setType(severityType type) {
        this.type = type;
    }

    /**
     * Gets the maximum points associated with the penalty.
     *
     * @return The maximum points associated with the penalty.
     */


    public Integer getMaxP() {
        return maxP;
    }

    /**
     * Sets the maximum points associated with the penalty.
     *
     * @param maxP The maximum points associated with the penalty.
     */


    public void setMaxP(Integer maxP) {
        this.maxP = maxP;
    }



    /**
     * Gets the law associated with the penalty.
     *
     * @return The law associated with the penalty.
     */

    public Integer getLaw() {
        return law;
    }

    /**
     * Sets the law associated with the penalty.
     *
     * @param law The law associated with the penalty.
     */



    public void setLaw(Integer law) {
        this.law = law;
    }


    /**
     * Gets the minimum points associated with the penalty.
     *
     * @return The minimum points associated with the penalty.
     */


    public Integer getMinP() {
        return minP;
    }

    /**
     * Sets the minimum points associated with the penalty.
     *
     * @param minP The minimum points associated with the penalty.
     */



    public void setMinP(Integer minP) {
        this.minP = minP;
    }

    /**
     * Gets the minimum quantity associated with the penalty.
     *
     * @return The minimum quantity associated with the penalty.
     */


    public Float getMinQ() {
        return minQ;
    }

    /**
     * Sets the minimum quantity associated with the penalty.
     *
     * @param minQ The minimum quantity associated with the penalty.
     */


    public void setMinQ(Float minQ) {
        this.minQ = minQ;
    }

    /**
     * Gets the maximum quantity associated with the penalty.
     *
     * @return The maximum quantity associated with the penalty.
     */

    public Float getMaxQ() {
        return maxQ;
    }

    /**
     * Sets the maximum quantity associated with the penalty.
     *
     * @param maxQ The maximum quantity associated with the penalty.
     */


    public void setMaxQ(Float maxQ) {
        this.maxQ = maxQ;
    }

    /**
     * Gets the reason for the penalty.
     *
     * @return The reason for the penalty.
     */


    public typeof getReason() {
        return reason;
    }

    /**
     * Sets the reason for the penalty.
     *
     * @param reason The reason for the penalty.
     */



    public void setReason(typeof reason) {
        this.reason = reason;
    }


    /**
     * Returns a string representation of the ListPenaltyDTO object.
     *
     * @return A string representation of the ListPenaltyDTO object.
     */



    @NonNull
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
