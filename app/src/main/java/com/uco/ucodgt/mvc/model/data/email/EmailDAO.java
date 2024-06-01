package com.uco.ucodgt.mvc.model.data.email;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uco.ucodgt.mvc.model.business.email.EmailDTO;
import com.uco.ucodgt.mvc.model.data.EmailCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * The EmailDAO class handles the sending of emails using the Volley library calling Integromat system.
 * @author Alfonso de la torre
 */
public class EmailDAO {

    RequestQueue requestQueue;

    /**
     * Sends an email using the provided EmailDTO object and context.
     *
     * @param email the EmailDTO object containing email details
     * @param applicationContext the application context used to initialize the RequestQueue
     * @param emailCallback the callback interface to handle success or error responses
     */
    public void sendEmail(EmailDTO email, Context applicationContext, EmailCallback emailCallback) {
        requestQueue= Volley.newRequestQueue(applicationContext);
        sendEmail(email, new EmailCallback() {
            @Override
            public void onEmailSended(EmailDTO email) {
                emailCallback.onEmailSended(email);
            }

            @Override
            public void onError(VolleyError error) {
                emailCallback.onError(error);
            }
        });
    }


    /**
     * Sends an email using the provided EmailDTO object.
     *
     * @param email the EmailDTO object containing email details
     * @param emailCallback the callback interface to handle success or error responses
     */
    public void sendEmail(EmailDTO email,EmailCallback emailCallback){
        String URL="https://hook.eu2.make.com/r6lq1o7ox2wdlc4kk97v5ba846grx94l";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    emailCallback.onEmailSended(email);
                },
                emailCallback::onError
        ) {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<>();
                params.put("to", email.getTo());
                params.put("body",email.getBody());
                params.put("tittle",email.getSubject());
                return params;
            }
        };
        requestQueue.add(request);
    }
}
