package com.uco.ucodgt.mvc.model.data;

import com.android.volley.VolleyError;
import com.uco.ucodgt.mvc.model.business.email.EmailDTO;
import com.uco.ucodgt.mvc.model.data.email.EmailDAO;

public interface EmailCallback {
    void onEmailSended(EmailDTO email);

    void onError(VolleyError error);
}
