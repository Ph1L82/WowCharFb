package cl.philipsoft.ph1l.wowcharfb.views.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;

import java.util.Arrays;

import cl.philipsoft.ph1l.wowcharfb.BuildConfig;
import cl.philipsoft.ph1l.wowcharfb.R;
import cl.philipsoft.ph1l.wowcharfb.data.CurrentUser;
import cl.philipsoft.ph1l.wowcharfb.data.Nodes;
import cl.philipsoft.ph1l.wowcharfb.models.User;
import cl.philipsoft.ph1l.wowcharfb.views.MainActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity implements LoginCallback {
    private static final int RC_SIGN_IN = 666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new LoginValidation(this).validate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                signedIn();
                finish();
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Log.d("ERROR", "LOGIN onActivityResult: Usuario canceló ingreso.");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Log.d("ERROR", "LOGIN onActivityResult: no hay conexión.");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Log.d("ERROR", "LOGIN onActivityResult: error desconocido.");
                    return;
                }
            }
        }
    }

    @Override
    public void signedIn() {
        User user = new User();
        CurrentUser currentUser = new CurrentUser();
        user.setUid(currentUser.userID());
        user.setEmail(currentUser.email());
        user.setName(currentUser.name());
        user.setPhoto(currentUser.photo());
        user.setAuthProviderID(currentUser.authProviderID());
        new Nodes().users().child(user.getUid()).setValue(user);
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void signIn() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()))
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                        .setTheme(R.style.FullscreenTheme_NoActionBar)
                        .build(),
                RC_SIGN_IN);
    }
}
