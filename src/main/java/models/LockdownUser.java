package models;

/**
 * Contributor(s): Luciano K
 * Description:
 */
public class LockdownUser {
    private String id; // Discord User ID
    private String password; // Password
    private boolean isLoggedIn; // [true = logged in] - [false = logged out]

    public LockdownUser(String id, String password, boolean isLoggedIn) {
        this.id = id;
        this.password = password;
        this.isLoggedIn = isLoggedIn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
