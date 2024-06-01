package com.uco.ucodgt.mvc.model.business.email;

import android.content.Context;

import com.android.volley.VolleyError;
import com.uco.ucodgt.mvc.model.data.EmailCallback;
import com.uco.ucodgt.mvc.model.data.email.EmailDAO;

/**
 * The ManagerEmail class is responsible for managing email operations,
 * acting as an intermediary between the business logic and the data layer.
 * @author Alfonso de la torre
 */
public class ManagerEmail {

    /**
     * Sends an email using the provided EmailDTO object and context.
     *
     * @param email the EmailDTO object containing email details
     * @param applicationContext the application context used to initialize the RequestQueue
     * @param callback the callback interface to handle success or error responses
     */
    public void sendEmail(EmailDTO email, Context applicationContext, EmailCallback callback){
        EmailDAO emD=new EmailDAO();
        emD.sendEmail(email,applicationContext, new EmailCallback() {
            @Override
            public void onEmailSended(EmailDTO email) {
                callback.onEmailSended(email);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }
        });
    }
}
