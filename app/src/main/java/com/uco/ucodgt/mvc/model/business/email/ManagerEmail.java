package com.uco.ucodgt.mvc.model.business.email;

import android.content.Context;

import com.android.volley.VolleyError;
import com.uco.ucodgt.mvc.model.data.EmailCallback;
import com.uco.ucodgt.mvc.model.data.email.EmailDAO;

public class ManagerEmail {

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
