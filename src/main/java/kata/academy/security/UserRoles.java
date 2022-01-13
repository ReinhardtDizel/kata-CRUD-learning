package kata.academy.security;

public enum UserRoles {
    ADMIN("ROLE_ADMIN"),
    MANAGER("ROLE_MANAGER"),
    USER("ROLE_USER");

    private final String value;

    UserRoles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
