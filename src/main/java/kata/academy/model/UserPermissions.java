package kata.academy.model;

public enum UserPermissions {
    ADMIN("ROLE_ADMIN"),
    MANAGER("ROLE_MANAGER"),
    USER("ROLE_USER"),
    ANONYMOUS("ROLE_ANONYMOUS");

    private final String value;

    UserPermissions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
