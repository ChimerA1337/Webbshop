package application;

public class User {
    private int userid;
    private String username;
    private PermissionLevel permissionLevel;

    public User(int userId, String username, PermissionLevel permissionLevel) {
        this.userid = userid;
        this.username = username;
        this.permissionLevel = permissionLevel;
    }

    public int getUserid() { return userid; }
    //public void setUserid(int userid) { this.userid = userid; }
    public String getUsername() { return username; }
    //public void setUsername(String username) { this.username = username; }
    public PermissionLevel getPermissionlevel() { return permissionLevel; }
    //public void setPermissionLevel(PermissionLevel permissionLevel) { this.permissionLevel = permissionLevel; }


}
