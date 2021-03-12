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
}
