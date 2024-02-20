package mvc.model.business.user.client;

import java.util.Date;

public class ClientDTO {

    private String dni;

    private String password;

    private String name;

    private String surname;

    private Date age;

    private String email;

    private Integer licencepoints;
    public ClientDTO(){

    }

    public ClientDTO(String dni, String password, String name, String surname, Date age, String email, Integer licencepoints){
        this.dni=dni;
        this.password=password;
        this.name=name;
        this.surname=surname;
        this.age=age;
        this.email=email;
        this.licencepoints=licencepoints;
    }

    public Integer getLicencepoints() {
        return licencepoints;
    }

    public void setLicencepoints(Integer licencepoints) {
        this.licencepoints = licencepoints;
    }

    public String getDni() {
        return dni;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSurname() {
        return surname;
    }

    public Date getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }


    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Date age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }



    @Override
    public String toString() {
        return "UserDTO{" +
                "dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", licencepoints=" + licencepoints +
                '}';
    }
}

