package cl.philipsoft.ph1l.wowcharfb.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by phil_ on 26-03-2017.
 */

public class CurrentUser {

    public FirebaseUser get() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public String email() {
        return get().getEmail();
    }

    public String name() {
        return get().getDisplayName();
    }

    public String userID() {
        return get().getUid();
    }

    public String photo() {
        if (get().getPhotoUrl() != null) {
            return get().getPhotoUrl().toString();
        } else {
            return "https://eu.battle.net/forums/static/images/game-logos/game-logo-wow.png";
        }
    }

    public String authProviderID() {
        return get().getProviderId();
    }
}
