package com.example.ucodgt.mvc.model.business.user.admin;

import androidx.annotation.NonNull;

import java.util.Date;


/**
 * Represents an administrator user in the system.
 * This class encapsulates the data associated with an administrator user,
 * including their unique identification number (DNI), password, name, surname, age,
 * and email address.
 * @author Alfonso de la torre
 */
public class AdminDTO {


    private String dni;

    private String password;

    private String name;

    private String surname;

    private Date age;

    private String email;

    /**
     * Default constructor.
     */
    public AdminDTO(){

    }

    /**
     * Constructor with parameters to initialize all attributes of the administrator user.
     *
     * @param dni the unique identification number (DNI) of the administrator user.
     * @param password the password of the administrator user.
     * @param name the name of the administrator user.
     * @param surname the surname of the administrator user.
     * @param age the age of the administrator user.
     * @param email the email address of the administrator user.
     */
    public AdminDTO(String dni, String password, String name, String surname, Date age, String email){
        this.dni=dni;
        this.password=password;
        this.name=name;
        this.surname=surname;
        this.age=age;
        this.email=email;
    }


    /**
     * Retrieves the unique identification number (DNI) of the administrator user.
     *
     * @return the DNI of the administrator user.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Retrieves the name of the administrator user.
     *
     * @return the name of the administrator user.
     */

    public String getName() {
        return name;
    }


    /**
     * Retrieves the password of the administrator user.
     *
     * @return the password of the administrator user.
     */

    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the surname of the administrator user.
     *
     * @return the surname of the administrator user.
     */

    public String getSurname() {
        return surname;
    }

    /**
     * Retrieves the age of the administrator user.
     *
     * @return the age of the administrator user.
     */

    public Date getAge() {
        return age;
    }

    /**
     * Retrieves the email address of the administrator user.
     *
     * @return the email address of the administrator user.
     */

    public String getEmail() {
        return email;
    }

    /**
     * Sets the unique identification number (DNI) of the administrator user.
     *
     * @param dni the DNI to set for the administrator user.
     */

    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Sets the password of the administrator user.
     *
     * @param password the password to set for the administrator user.
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the name of the administrator user.
     *
     * @param name the name to set for the administrator user.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the age of the administrator user.
     *
     * @param age the age to set for the administrator user.
     */

    public void setAge(Date age) {
        this.age = age;
    }

    /**
     * Sets the email address of the administrator user.
     *
     * @param email the email address to set for the administrator user.
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the surname of the administrator user.
     *
     * @param surname the surname to set for the administrator user.
     */

    public void setSurname(String surname) {
        this.surname = surname;
    }


    /**
     * Returns a string representation of the administrator user.
     *
     * @return a string representation of the administrator user.
     */
    @NonNull
    @Override
    public String toString() {
        return "UserDTO{" +
                "dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}

