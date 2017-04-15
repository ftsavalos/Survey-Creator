package com.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.models.survey.Survey;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 4, message = "USERNAME must be at least 4 chars !!!")
    @Column(unique = true)
    private String username;

    @NotNull
    @NotEmpty(message = "PASSWORD must be entered")
    @Size(min = 6, message = "PASSWORD must be at least 6 chars !!!")
    private String password;

    @NotNull
    @NotEmpty(message = "First name must be entered")
    private String firstName;

    @NotNull
    @NotEmpty(message = "Last name must be entered")
    private String lastName;

    @NotNull
    @NotEmpty(message = "Email must be entered")
    @Email(message = "Invalid email address")
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccessedDate;
    private boolean enabled;
    private boolean tokenExpired;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Survey> surveys;

    //
    //Constructors
    //
    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this(username);
        this.password = password;
    }

    public User(String username, String password, String email) {
        this(username,password);
        this.email = email;
    }

    public User(String username, String password, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.tokenExpired = false;
    }

    //
    //OnCreate Dates
    //
    @PrePersist
    public void onCreate() {
        setCreationDate(new Date());
    }

    //
    //Accessors
    //
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastAccessedDate() {
        return lastAccessedDate;
    }

    public void setLastAccessedDate(Date lastAccessedDate) {
        this.lastAccessedDate = lastAccessedDate;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(boolean tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    //
    //Overridden Methods
    //
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //TODO
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", creationDate=" + creationDate +
                ", lastAccessedDate=" + lastAccessedDate +
                ", enabled=" + enabled +
                ", tokenExpired=" + tokenExpired +
                '}';
    }
}