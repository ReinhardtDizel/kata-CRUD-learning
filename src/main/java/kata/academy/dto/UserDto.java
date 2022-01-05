package kata.academy.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private List<String> roles = new ArrayList<>();

    public UserDto(Long id, String name, String email, String password, List<String> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String roleString() {
        StringBuilder stringBuilder = new StringBuilder();
        roles.forEach(s -> stringBuilder.append(s).append(", "));
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return id + " name: " + name + " emil: " + email + " role: " + roleString();
    }
}
