package com.uco.ucodgt.mvc.model.business.user.client;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;


/**
 * Represents a client user in the system.
 * This class encapsulates the data associated with a client user,
 * including their unique identification number (DNI), password, name, surname, age,
 * email address, date of obtaining the driver's license, date of last update, and accumulated license points.
 * @author Alfonso de la torre
 */
public class ClientDTO implements Serializable {

    private String dni;

    private String password;

    private String name;

    private Date dateLicenceObtaining;
    private String surname;

    private Date age;

    private String email;

    private Date dateLastUpdate;

    private Integer licencepoints;

    /**
     * Default constructor for ClientDTO.
     */
    public ClientDTO(){

    }


    /**
     * Constructor with parameters to initialize all attributes of the client user.
     *
     * @param dni The unique identification number (DNI) of the client user.
     * @param password The password of the client user.
     * @param name The name of the client user.
     * @param surname The surname of the client user.
     * @param age The age of the client user.
     * @param email The email address of the client user.
     * @param licencepoints The accumulated license points of the client user.
     */
    public ClientDTO(String dni, String password, String name, String surname, Date age, String email, Integer licencepoints){
        this.dni=dni;
        this.password=password;
        this.name=name;
        this.surname=surname;
        this.age=age;
        this.email=email;
        this.licencepoints=licencepoints;
        this.dateLicenceObtaining=new Date();
        this.dateLastUpdate=new Date();

    }

    /**
     * Retrieves the date when the driver's license was obtained by the client user.
     *
     * @return The date when the driver's license was obtained.
     */
    public Date getDateLicenceObtaining() {
        return dateLicenceObtaining;
    }

    /**
     * Sets the date when the driver's license was obtained by the client user.
     *
     * @param dateLicenceObtaining The date when the driver's license was obtained.
     */

    public void setDateLicenceObtaining(Date dateLicenceObtaining) {
        this.dateLicenceObtaining = dateLicenceObtaining;
    }

    /**
     * Retrieves the accumulated license points of the client user.
     *
     * @return The accumulated license points of the client user.
     */
    public Integer getLicencepoints() {
        return licencepoints;
    }

    /**
     * Sets the accumulated license points of the client user.
     *
     * @param licencepoints The accumulated license points to set.
     */

    public void setLicencepoints(Integer licencepoints) {
        this.licencepoints = licencepoints;
    }

    /**
     * Retrieves the unique identification number (DNI) of the client user.
     *
     * @return The DNI of the client user.
     */

    public String getDni() {
        return dni;
    }

    /**
     * Retrieves the name of the client user.
     *
     * @return The name of the client user.
     */

    public String getName() {
        return name;
    }

    /**
     * Retrieves the password of the client user.
     *
     * @return The password of the client user.
     */

    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the surname of the client user.
     *
     * @return The surname of the client user.
     */

    public String getSurname() {
        return surname;
    }

    /**
     * Retrieves the age of the client user.
     *
     * @return The age of the client user.
     */

    public Date getAge() {
        return age;
    }

    /**
     * Retrieves the email address of the client user.
     *
     * @return The email address of the client user.
     */

    public String getEmail() {
        return email;
    }

    /**
     * Sets the unique identification number (DNI) of the client user.
     *
     * @param dni The DNI to set for the client user.
     */


    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Sets the password of the client user.
     *
     * @param password The password to set for the client user.
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the name of the client user.
     *
     * @param name The name to set for the client user.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the age of the client user.
     *
     * @param age The age to set for the client user.
     */

    public void setAge(Date age) {
        this.age = age;
    }

    /**
     * Sets the email address of the client user.
     *
     * @param email The email address to set for the client user.
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the surname of the client user.
     *
     * @param surname The surname to set for the client user.
     */

    public void setSurname(String surname) {
        this.surname = surname;
    }


    /**
     * Retrieves the date of the last update made to the client user's information.
     *
     * @return The date of the last update.
     */
    public Date getDateLastUpdate() {
        return dateLastUpdate;
    }

    /**
     * Sets the date of the last update made to the client user's information.
     *
     * @param dateLastUpdate The date of the last update to set.
     */
    public void setDateLastUpdate(Date dateLastUpdate) {
        this.dateLastUpdate = dateLastUpdate;
    }

    /**
     * Returns a string representation of the client user.
     *
     * @return A string representation of the client user.
     */
    @NonNull
    @Override
    public String toString() {
        return "ClientDTO{" +
                "dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", licencepoints=" + licencepoints +
                '}';
    }
}

