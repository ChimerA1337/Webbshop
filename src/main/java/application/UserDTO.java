package application;

public final class UserDTO {
    private final int userid;
    private final String username;
    private final PermissionLevel permissionlevel;

    public UserDTO(int userid, String username, PermissionLevel permissionlevel) {
        this.userid = userid;
        this.username = username;
        this.permissionlevel = permissionlevel;
    }

    public int getUserid() { return userid; }
    public String getUsername() { return username; }
    public PermissionLevel getPermissionlevel() { return permissionlevel; }
}
