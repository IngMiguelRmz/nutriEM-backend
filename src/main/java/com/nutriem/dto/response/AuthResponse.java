package com.nutriem.dto.response;

public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private Long   id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;

    public AuthResponse() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String token, type = "Bearer", email, firstName, lastName, role;
        private Long id;

        public Builder token(String v)      { this.token = v; return this; }
        public Builder type(String v)       { this.type = v; return this; }
        public Builder id(Long v)           { this.id = v; return this; }
        public Builder email(String v)      { this.email = v; return this; }
        public Builder firstName(String v)  { this.firstName = v; return this; }
        public Builder lastName(String v)   { this.lastName = v; return this; }
        public Builder role(String v)       { this.role = v; return this; }

        public AuthResponse build() {
            AuthResponse r = new AuthResponse();
            r.token = token; r.type = type; r.id = id;
            r.email = email; r.firstName = firstName;
            r.lastName = lastName; r.role = role;
            return r;
        }
    }

    public String getToken()        { return token; }
    public void setToken(String v)  { this.token = v; }
    public String getType()         { return type; }
    public void setType(String v)   { this.type = v; }
    public Long getId()             { return id; }
    public void setId(Long v)       { this.id = v; }
    public String getEmail()        { return email; }
    public void setEmail(String v)  { this.email = v; }
    public String getFirstName()    { return firstName; }
    public void setFirstName(String v) { this.firstName = v; }
    public String getLastName()     { return lastName; }
    public void setLastName(String v)  { this.lastName = v; }
    public String getRole()         { return role; }
    public void setRole(String v)   { this.role = v; }
}
