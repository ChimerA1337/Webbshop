package application;

public class User {
    private final int userid;
    private String username;
    private PermissionLevel permissionLevel;

    public User(int userid, String username, PermissionLevel permissionLevel) {
        this.userid = userid;
        this.username = username;
        this.permissionLevel = permissionLevel;
    }

    public int getUserid() { return userid; }
    public String getUsername() { return username; }
    public PermissionLevel getPermissionlevel() { return permissionLevel; }
}
