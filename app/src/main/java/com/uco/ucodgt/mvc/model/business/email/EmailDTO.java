package com.uco.ucodgt.mvc.model.business.email;

import java.io.Serializable;

/**
 * The EmailDTO class is a Data Transfer Object that represents the details of an email.
 * It implements the Serializable interface to allow for easy serialization.
 * @author Alfonso de la torre
 */
public class EmailDTO implements Serializable {

    private String to;

    private String body;

    private String subject;

    /**
     * Default constructor for EmailDTO.
     */
    public EmailDTO() {
        // Default constructor
    }

    /**
     * Constructs an EmailDTO with the specified recipient, body, and subject.
     *
     * @param to the recipient's email address
     * @param body the body of the email
     * @param subject the subject of the email
     */
    public EmailDTO(String to, String body,String subject){
        this.body=body;
        this.to=to;
        this.subject=subject;
    }

    /**
     * Gets the body of the email.
     *
     * @return the body of the email
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the body of the email.
     *
     * @param body the body of the email
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Gets the subject of the email.
     *
     * @return the subject of the email
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject of the email.
     *
     * @param subject the subject of the email
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets the recipient's email address.
     *
     * @return the recipient's email address
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets the recipient's email address.
     *
     * @param to the recipient's email address
     */
    public void setTo(String to) {
        this.to = to;
    }
}
