package cl.philipsoft.ph1l.wowcharfb.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by phil_ on 26-03-2017.
 */

public class Nodes {

    private DatabaseReference root() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference users() {
        return root().child("users");
    }

    public DatabaseReference userByUid(String uid) {
        return users().child(uid);
    }

    public DatabaseReference userCharacters(String uid) {
        return root().child("characters").child(uid);
    }
}
