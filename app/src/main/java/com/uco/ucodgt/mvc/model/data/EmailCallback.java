package com.uco.ucodgt.mvc.model.data;

import com.android.volley.VolleyError;
import com.uco.ucodgt.mvc.model.business.email.EmailDTO;
import com.uco.ucodgt.mvc.model.data.email.EmailDAO;
/**
 * This interface defines callback methods for handling the results of sending an email.
 * Implement this interface to handle the successful sending of an email or to handle errors
 * that may occur during the email sending process.
 * @author Alfonso de la torre
 */
public interface EmailCallback {
    /**
     * Called when an email has been successfully sent.
     *
     * @param email the EmailDTO object containing the details of the sent email
     */
    void onEmailSended(EmailDTO email);

    /**
     * Called when there is an error sending the email.
     *
     * @param error the VolleyError object containing the details of the error
     */
    void onError(VolleyError error);
}
