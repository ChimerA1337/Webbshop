package application;

public class User {
    private int userid;
    private String username;
    private PermissionLevel permissionLevel;

    public User(int userId, String username, PermissionLevel permissionLevel) {
        this.userid = userId;
        this.username = username;
        this.permissionLevel = permissionLevel;
    }

    public int getUserid() { return userid; }
    public String getUsername() { return username; }
    public PermissionLevel getPermissionlevel() { return permissionLevel; }
}
