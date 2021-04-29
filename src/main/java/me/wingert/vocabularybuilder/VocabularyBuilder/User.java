package me.wingert.vocabularybuilder.VocabularyBuilder;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "display_name")
    private String displayName;

    @NotNull
    private String email;

    @Column(name = "is_email_verified")
    private boolean isEmailVerified;

    @Column(name = "firebase_id")
    private String firebaseId;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "account_created_date_time")
    private String accountCreatedDateTime;

    @Column(name = "last_sign_in_date_time")
    private String lastSignInDateTime;


    public User() {}

    public User(String email, String firebaseId)
    {
        this.email = email;
        this.firebaseId = firebaseId;
    }

    public User(String displayName,
                String email,
                boolean isEmailVerified,
                String firebaseId,
                String providerId,
                String accountCreatedDateTime,
                String lastSignInDateTime)
    {
        this.displayName = displayName;
        this.email = email;
        this.isEmailVerified = isEmailVerified;
        this.firebaseId = firebaseId;
        this.providerId = providerId;
        this.accountCreatedDateTime = accountCreatedDateTime;
        this.lastSignInDateTime = lastSignInDateTime;
    }

    // TODO remove this constructor (probably)
    public User(String firstName,
                String lastName,
                String displayName,
                String email,
                boolean isEmailVerified,
                String firebaseId,
                String providerId,
                String accountCreatedDateTime,
                String lastSignInDateTime)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.email = email;
        this.isEmailVerified = isEmailVerified;
        this.firebaseId = firebaseId;
        this.providerId = providerId;
        this.accountCreatedDateTime = accountCreatedDateTime;
        this.lastSignInDateTime = lastSignInDateTime;
    }

    public void setId(int id) { this.id = id; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public void setEmail(String email) { this.email = email; }

    public void setIsEmailVerified(boolean verified) { isEmailVerified = verified; }

    public void setFirebaseId(String firebaseId) { this.firebaseId = firebaseId; }

    public void setProviderId(String providerId) { this.providerId = providerId; }

    public void setAccountCreatedDateTime(String creationDateTime) { accountCreatedDateTime = creationDateTime; }

    public void setLastSignInDateTime(String lastSignInDateTime) { this.lastSignInDateTime = lastSignInDateTime; }

    public int getId() { return id; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getDisplayName() { return displayName; }

    public String getEmail() { return email; }

    public boolean getIsEmailVerified() { return isEmailVerified; }

    public String getFirebaseId() { return firebaseId; }

    public String getProviderId() { return providerId; }

    public String getAccountCreatedDateTime() { return accountCreatedDateTime; }

    public String getLastSignInDateTime() { return lastSignInDateTime; }

    @Override
    public String toString()
    {
        return id + " " + displayName + " " + email + " " + firebaseId;
    }

}
