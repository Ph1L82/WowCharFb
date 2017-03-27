package cl.philipsoft.ph1l.wowcharfb.views.login;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by phil_ on 27-03-2017.
 */

public class LoginValidation {
    private LoginCallback loginCallback;

    public LoginValidation(LoginCallback loginCallback) {
        this.loginCallback = loginCallback;
    }

    public void validate() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            loginCallback.signedIn();
        } else {
            loginCallback.signIn();
        }
    }
}
