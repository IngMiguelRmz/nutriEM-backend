package com.nutriem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    private String lastName;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    private String licenseNumber;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.NUTRIOLOGIST;

    private boolean enabled = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "nutriologist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Patient> patients = new HashSet<>();

    // ── Constructors ──────────────────────────────────────────
    public User() {}

    public User(Long id, String firstName, String lastName, String email,
                String password, String licenseNumber, String phoneNumber,
                Role role, boolean enabled, LocalDateTime createdAt,
                LocalDateTime updatedAt, Set<Patient> patients) {
        this.id = id; this.firstName = firstName; this.lastName = lastName;
        this.email = email; this.password = password;
        this.licenseNumber = licenseNumber; this.phoneNumber = phoneNumber;
        this.role = role; this.enabled = enabled;
        this.createdAt = createdAt; this.updatedAt = updatedAt;
        this.patients = patients;
    }

    // ── Builder ───────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String firstName, lastName, email, password, licenseNumber, phoneNumber;
        private Role role = Role.NUTRIOLOGIST;
        private boolean enabled = true;

        public Builder id(Long id)                     { this.id = id; return this; }
        public Builder firstName(String v)             { this.firstName = v; return this; }
        public Builder lastName(String v)              { this.lastName = v; return this; }
        public Builder email(String v)                 { this.email = v; return this; }
        public Builder password(String v)              { this.password = v; return this; }
        public Builder licenseNumber(String v)         { this.licenseNumber = v; return this; }
        public Builder phoneNumber(String v)           { this.phoneNumber = v; return this; }
        public Builder role(Role v)                    { this.role = v; return this; }
        public Builder enabled(boolean v)              { this.enabled = v; return this; }

        public User build() {
            User u = new User();
            u.id = this.id; u.firstName = this.firstName; u.lastName = this.lastName;
            u.email = this.email; u.password = this.password;
            u.licenseNumber = this.licenseNumber; u.phoneNumber = this.phoneNumber;
            u.role = this.role; u.enabled = this.enabled;
            return u;
        }
    }

    // ── Getters & Setters ─────────────────────────────────────
    public Long getId()                        { return id; }
    public void setId(Long id)                 { this.id = id; }
    public String getFirstName()               { return firstName; }
    public void setFirstName(String v)         { this.firstName = v; }
    public String getLastName()                { return lastName; }
    public void setLastName(String v)          { this.lastName = v; }
    public String getEmail()                   { return email; }
    public void setEmail(String v)             { this.email = v; }
    public String getPassword()                { return password; }
    public void setPassword(String v)          { this.password = v; }
    public String getLicenseNumber()           { return licenseNumber; }
    public void setLicenseNumber(String v)     { this.licenseNumber = v; }
    public String getPhoneNumber()             { return phoneNumber; }
    public void setPhoneNumber(String v)       { this.phoneNumber = v; }
    public Role getRole()                      { return role; }
    public void setRole(Role v)                { this.role = v; }
    public boolean isEnabled()                 { return enabled; }
    public void setEnabled(boolean v)          { this.enabled = v; }
    public LocalDateTime getCreatedAt()        { return createdAt; }
    public LocalDateTime getUpdatedAt()        { return updatedAt; }
    public Set<Patient> getPatients()          { return patients; }
    public void setPatients(Set<Patient> v)    { this.patients = v; }
    public String getFullName()                { return firstName + " " + lastName; }

    public enum Role { ADMIN, NUTRIOLOGIST }
}
