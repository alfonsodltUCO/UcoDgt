package com.example.ucodgt.mvc.model.business.user.worker;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;


/**
 * Represents a worker user in the system.
 * This class encapsulates the data associated with a worker user,
 * including their unique identification number (DNI), password, name, surname, age,
 * and email address.
 * @author Alfonso de la torre
 */
public class WorkerDTO implements Serializable {

    private String dni;

    private String password;

    private String name;

    private String surname;

    private Date age;

    private String email;

    /**
     * Default constructor for WorkerDTO.
     */
    private Integer numberOfWorker;
    public WorkerDTO(){

    }

    /**
     * Constructor with parameters to initialize all attributes of the worker user.
     *
     * @param dni The unique identification number (DNI) of the worker user.
     * @param password The password of the worker user.
     * @param name The name of the worker user.
     * @param surname The surname of the worker user.
     * @param age The age of the worker user.
     * @param email The email address of the worker user.
     * @param numberOfWorker The number of the worker.
     */
    public WorkerDTO(String dni,String password,String name, String surname, Date age, String email,Integer numberOfWorker){
        this.dni=dni;
        this.password=password;
        this.name=name;
        this.surname=surname;
        this.age=age;
        this.email=email;
        this.numberOfWorker=numberOfWorker;
    }

    /**
     * Retrieves the number of the worker.
     *
     * @return The number of the worker.
     */
    public Integer getNumberOfWorker() {
        return numberOfWorker;
    }

    /**
     * Sets the number of the worker.
     *
     * @param numberOfWorker The number of the worker to set.
     */
    public void setNumberOfWorker(Integer numberOfWorker) {
        this.numberOfWorker = numberOfWorker;
    }

    /**
     * Retrieves the unique identification number (DNI) of the worker user.
     *
     * @return The DNI of the worker user.
     */

    public String getDni() {
        return dni;
    }

    /**
     * Retrieves the name of the worker user.
     *
     * @return The name of the worker user.
     */

    public String getName() {
        return name;
    }

    /**
     * Retrieves the password of the worker user.
     *
     * @return The password of the worker user.
     */

    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the surname of the worker user.
     *
     * @return The surname of the worker user.
     */

    public String getSurname() {
        return surname;
    }

    /**
     * Retrieves the age of the worker user.
     *
     * @return The age of the worker user.
     */
    public Date getAge() {
        return age;
    }

    /**
     * Retrieves the email address of the worker user.
     *
     * @return The email address of the worker user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the unique identification number (DNI) of the worker user.
     *
     * @param dni The DNI of the worker user to set.
     */

    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Sets the password of the worker user.
     *
     * @param password The password of the worker user to set.
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the name of the worker user.
     *
     * @param name The name of the worker user to set.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the age of the worker user.
     *
     * @param age The age of the worker user to set.
     */

    public void setAge(Date age) {
        this.age = age;
    }

    /**
     * Sets the email address of the worker user.
     *
     * @param email The email address of the worker user to set.
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the surname of the worker user.
     *
     * @param surname The surname of the worker user to set.
     */

    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returns a string representation of the worker user, including their DNI, name, surname, age, and email.
     *
     * @return A string representation of the worker user.
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
