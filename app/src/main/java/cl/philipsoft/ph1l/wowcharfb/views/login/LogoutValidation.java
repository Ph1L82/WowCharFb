package cl.philipsoft.ph1l.wowcharfb.views.login;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by phil_ on 27-03-2017.
 */

public class LogoutValidation {
    private LogoutCallback logoutCallback;

    public LogoutValidation(LogoutCallback logoutCallback) {
        this.logoutCallback = logoutCallback;
    }

    public void validate() {
        Log.d("WOWC:LOGOUT", "validate: INICIANDO VALIDACION DE SALIDA");
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Log.d("WOWC:LOGOUT", "validate: FirebaseAuth.getInstance().getCurrentUser() == null");
            logoutCallback.signOut();
        }
    }
}
