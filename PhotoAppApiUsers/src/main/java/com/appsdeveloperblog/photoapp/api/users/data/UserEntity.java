package com.appsdeveloperblog.photoapp.api.users.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -5223076043374572152L;

    @Id // will be db id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // will be autogenerated / auto incrementing
    private long id;
    @Column(nullable=false, length=50)
    private String firstName;
    @Column(nullable=false, length=50)
    private String lastName;
    @Column(nullable=false, length=120, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false, unique = true)
    private String encryptedPassword;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
