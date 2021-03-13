package managers;

import core.Bot;
import models.LockdownServer;
import models.LockdownUser;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import utils.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Contributor(s): Luciano K
 * Description:
 */
public class AuthenticationManager {
    private Bot bot;

    private ArrayList<LockdownUser> loggedInUsers;
    private ScheduledExecutorService service;

    public AuthenticationManager(Bot bot) { // TODO: set bot status to the amount of users logged in
        this.bot = bot;

        loggedInUsers = new ArrayList<>();
        service = Executors.newScheduledThreadPool(5);
    }

    /**
     * Logs a user in. Gives the user roles in the
     * servers they are authed in and updates the database.
     * @param user
     */
    public void login(LockdownUser user) {
        // add to loggedIn arraylist
        user.setLoggedIn(true);
        loggedInUsers.add(user);

        // update database to show that the user is logged in
        bot.getDatabaseApi().updateLockdownUser(user);

        // add a 15 min timer that will execute logout() when its up
        service.schedule(() -> logout(user), Integer.parseInt(bot.getConfig().get("LOGOUT_DELAY")), TimeUnit.MINUTES);

        // notify servers
        notifyServers(user.getId(), Action.LOGIN);
    }

    /**
     * Logs a user out. This will remove their roles from the
     * servers they have access to and update the database.
     * @param user      user you wish to logout
     */
    public void logout(LockdownUser user) {
        // remove user from loggedIn arraylist
        user.setLoggedIn(false);

        boolean check = false;
        LockdownUser remove = null;
        for(LockdownUser u : loggedInUsers) {
            if(u.getId().equals(user.getId())) {
                check = true;
                remove = u; // avoids ConcurrentModificationException
            }
        }

        // If check is false, this means that the user has already logged out completely.
        if(!check) {
            return;
        } else {
            loggedInUsers.remove(remove);
        }

        // update database to show that they are logged out
        bot.getDatabaseApi().updateLockdownUser(user);

        // Notify servers
        notifyServers(user.getId(), Action.LOGOUT);
    }

    /**
     * Checks if a user is logged in.
     * @param id        id of user
     * @return          [true if logged in] [false if logged out]
     */
    public boolean isLoggedIn(String id) {
        for(LockdownUser user : loggedInUsers) {
            if(user.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Notifies servers when a user logs in/out
     * @param id        id of user
     * @param action    Action.LOGIN or Action.LOGOUT
     */
    private void notifyServers(String id, Action action) {
        // goes through all the servers & checks if server has user
        List<LockdownServer> servers = bot.getDatabaseApi().getServersThatHaveThisUser(id);

        // Checks if no servers contain this user, if so return
        if(servers.size() <= 0) {
            return;
        }


        for(LockdownServer server : servers) {
            // Get Guild
            Guild guild = bot.getJDA().getGuildById(server.getId());

            // Check if its null, if it is delete is from our db
            if(guild == null) {
                // Delete this server from our db
                System.out.println(server.getId() + " is null");
                return;
            }

            // Get Role - First check if id is not empty
            if(!server.getIdRole().isEmpty()) {
                // Get Role
                Role role = guild.getRoleById(server.getIdRole());

                // Make sure role is not null
                if(role != null) {
                    // Get member
                    Member member = guild.getMemberById(id);

                    // Make sure member is not null
                    if(member != null) {
                        if(guild.getSelfMember().canInteract(role)) {
                            if(action == Action.LOGIN) {
                                guild.addRoleToMember(member, role).queue();
                                log(guild, server.getIdLogChannel(), member.getEffectiveName() + " has logged in!");
                                continue;
                            }
                            if(action == Action.LOGOUT) { // TODO: BUG: If a user tries executing !logout while in a voicechannel, it will say that they successfully logged out, but this is not the case
                                // Check if they are in a voicechannel
                                if(member.getVoiceState().inVoiceChannel()) {
                                    // They are currently in a voicechannel, extend their time
                                    log(guild, server.getIdLogChannel(), member.getEffectiveName() + " is currently in a voice channel, extending their time...");
                                    login(bot.getDatabaseApi().getLockdownUser(id));
                                    continue;
                                }

                                guild.removeRoleFromMember(member, role).queue();
                                log(guild, server.getIdLogChannel(), member.getEffectiveName() + " has logged out!");
                                continue;
                            }

                        } else {
                            log(guild, server.getIdLogChannel(), "Can't interact with role: " + role.getName() + "! Place the bot role higher.");
                        }
                    }
                } else {
                    log(guild, server.getIdLogChannel(), "Role does not exist.");
                }
            }
        }
    }

    /**
     * Sends a message in the specified channel
     * @param guild         server the message is going to be sent in
     * @param channelId     channel the message is gonna be sent in
     * @param message       message that is gonna be sent
     */
    private void log(Guild guild, String channelId, String message) {
        // Check if channelId is empty
        if(channelId.isEmpty()) {
            return;
        }

        // Get channel
        TextChannel channel = guild.getTextChannelById(channelId);

        // If null, return
        if(channel == null) {
            return;
        }

        // If good, send message
        channel.sendMessage("``[LockdownBot]: " + message + "``").queue();
    }
}
