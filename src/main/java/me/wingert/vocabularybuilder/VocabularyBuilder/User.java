package me.wingert.vocabularybuilder.VocabularyBuilder;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "display_name")
    private String displayName;

    @NotNull
    private String email;

    @Column(name = "is_email_verified")
    private boolean isEmailVerified;

    @Column(name = "firebase_id")
    private String firebaseId;

    @Column(name = "account_created_date_time")
    private Timestamp accountCreatedDateTime;

    @Column(name = "last_sign_in_date_time")
    private Timestamp lastSignInDateTime;


    public User() { }

    public User(String displayName,
                String email,
                boolean isEmailVerified,
                String firebaseId,
                Timestamp accountCreatedDateTime,
                Timestamp lastSignInDateTime)
    {
        this.displayName = displayName;
        this.email = email;
        this.isEmailVerified = isEmailVerified;
        this.firebaseId = firebaseId;
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
                Timestamp accountCreatedDateTime,
                Timestamp lastSignInDateTime)
    {
        this.displayName = displayName;
        this.email = email;
        this.isEmailVerified = isEmailVerified;
        this.firebaseId = firebaseId;
        this.accountCreatedDateTime = accountCreatedDateTime;
        this.lastSignInDateTime = lastSignInDateTime;
    }

    public void setId(int id) { this.id = id; }

    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public void setEmail(String email) { this.email = email; }

    public void setIsEmailVerified(boolean verified) { isEmailVerified = verified; }

    public void setFirebaseId(String firebaseId) { this.firebaseId = firebaseId; }

    public void setAccountCreatedDateTime(Timestamp creationDateTime) { accountCreatedDateTime = creationDateTime; }

    public void setLastSignInDateTime(Timestamp lastSignInDateTime) { this.lastSignInDateTime = lastSignInDateTime; }

    public int getId() { return id; }

    public String getDisplayName() { return displayName; }

    public String getEmail() { return email; }

    public boolean getIsEmailVerified() { return isEmailVerified; }

    public String getFirebaseId() { return firebaseId; }

    public Timestamp getAccountCreatedDateTime() { return accountCreatedDateTime; }

    public Timestamp getLastSignInDateTime() { return lastSignInDateTime; }

    @Override
    public String toString()
    {
        return id + " " + displayName + " " + email + " " + firebaseId + " " + lastSignInDateTime.toString();
    }

}
