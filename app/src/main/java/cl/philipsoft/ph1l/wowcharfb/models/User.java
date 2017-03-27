package cl.philipsoft.ph1l.wowcharfb.models;

/**
 * Created by phil_ on 26-03-2017.
 */

public class User {

    private String name;
    private String uid;
    private String email;
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAuthProviderID() {
        return authProviderID;
    }

    public void setAuthProviderID(String authProviderID) {
        this.authProviderID = authProviderID;
    }

    private String authProviderID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {

    }
}
