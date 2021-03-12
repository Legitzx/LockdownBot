package models;

import java.util.List;

/**
 * Contributor(s): Luciano K
 * Description:
 */
public class LockdownServer {
    private String id; // Guild ID of server
    private List<String> authList; // User IDs of authenticated users
    private boolean status; // [locked = false] - [unlocked = true] - This affects the idRole permissions
    private String idLogChannel; // ID of textchannel to log events in
    private String idRole; // ID of the role that will give users access to channels

    public LockdownServer(String id, List<String> authList, boolean status, String idLogChannel, String idRole) {
        this.id = id;
        this.authList = authList;
        this.status = status;
        this.idLogChannel = idLogChannel;
        this.idRole = idRole;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getAuthList() {
        return authList;
    }

    public void setAuthList(List<String> authList) {
        this.authList = authList;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getIdLogChannel() {
        return idLogChannel;
    }

    public void setIdLogChannel(String idLogChannel) {
        this.idLogChannel = idLogChannel;
    }

    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }
}
