package com.uco.ucodgt.mvc.model.business.email;

import java.io.Serializable;

public class EmailDTO implements Serializable {

    private String to;

    private String body;

    private String subject;

    public EmailDTO(){

    }
    public EmailDTO(String to, String body,String subject){
        this.body=body;
        this.to=to;
        this.subject=subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
