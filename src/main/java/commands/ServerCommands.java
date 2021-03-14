package commands;

import core.Bot;
import models.LockdownServer;
import models.LockdownUser;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Contributor(s): Luciano K
 * Description:
 */
public class ServerCommands extends ListenerAdapter {
    private Bot bot;

    public ServerCommands(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        Member member = event.getMember();
        String guildId = event.getGuild().getId();

        if(member.getPermissions().contains(Permission.ADMINISTRATOR)) {
            String[] args = event.getMessage().getContentRaw().split(" ");

            if(args[0].equalsIgnoreCase(bot.getConfig().get("PREFIX") + "server")) {
                if(args.length < 2) {
                    event.getChannel().sendMessage("Incorrect usage").queue();
                    return;
                } else {
                    if(args[1].equalsIgnoreCase("add")) {
                        if(args.length < 3) {
                            event.getChannel().sendMessage("Incorrect usage").queue();
                            return;
                        } else {
                            List<Member> mentioned = event.getMessage().getMentionedMembers();

                            if(mentioned.size() <= 0) {
                                event.getChannel().sendMessage("Incorrect usage").queue();
                                return;
                            }

                            LockdownServer server = bot.getDatabaseApi().getLockdownServer(guildId);

                            for(Member m : mentioned) {
                                if(server.addUserToAuthList(m.getId())) {
                                    event.getChannel().sendMessage("Added " + m.getEffectiveName()).queue();
                                } else {
                                    event.getChannel().sendMessage("Error: " + m.getEffectiveName() + " is already in the database!").queue();
                                }
                            }

                            bot.getDatabaseApi().updateLockdownServer(server);
                        }
                        return;
                    }

                    if(args[1].equalsIgnoreCase("remove")) {
                        if(args.length < 3) {
                            event.getChannel().sendMessage("Incorrect usage").queue();
                            return;
                        } else {
                            List<Member> mentioned = event.getMessage().getMentionedMembers();

                            if(mentioned.size() <= 0) {
                                event.getChannel().sendMessage("Incorrect usage").queue();
                                return;
                            }

                            LockdownServer server = bot.getDatabaseApi().getLockdownServer(guildId);

                            for(Member m : mentioned) {
                                if(server.removeUserFromAuthList(m.getId())) {
                                    event.getChannel().sendMessage("Removed " + m.getEffectiveName()).queue();
                                } else {
                                    event.getChannel().sendMessage("Error: " + m.getEffectiveName() + " is not in the database!").queue();
                                }
                            }

                            bot.getDatabaseApi().updateLockdownServer(server);
                        }
                        return;
                    }

                    if(args[1].equalsIgnoreCase("list")) {
                        LockdownServer server = bot.getDatabaseApi().getLockdownServer(guildId);

                        if(server.getAuthList().isEmpty()) {
                            event.getChannel().sendMessage("No users are currently on the authentication list! Add users by executing ``!server add <@user>``").queue();
                            return;
                        }

                        StringBuilder builder = new StringBuilder();
                        List<String> deadUsers = new ArrayList<>(); // This will be full of members who are no longer in the server

                        for(String id : server.getAuthList()) {
                            LockdownUser user = bot.getDatabaseApi().getLockdownUser(id);

                            try {
                                String name = event.getGuild().getMemberById(id).getEffectiveName();
                            } catch (NullPointerException e) { // This means that the user is no longer in the server, remove them from authlist
                                deadUsers.add(id);
                                continue;
                            }

                            String name = event.getGuild().getMemberById(id).getEffectiveName();

                            // This means that the user is not in our database (the user has not registered yet)
                            if(user == null) {
                                builder.append(name + " - Has not created account" + "\n");
                                continue;
                            }

                            builder.append(name + " - Logged in: " + user.isLoggedIn() + "\n");
                        }

                        // Removes user who are no longer in the server
                        for(String id : deadUsers) {
                            server.removeUserFromAuthList(id);
                        }
                        if(deadUsers.size() > 0) {
                            bot.getDatabaseApi().updateLockdownServer(server);
                        }

                        event.getChannel().sendMessage(builder.toString()).queue();
                        return;
                    }

                    if(args[1].equalsIgnoreCase("lock")) {
                        // logs everyone out, prevents user from logging in
                    }

                    if(args[1].equalsIgnoreCase("unlock")) {
                        // Removes restriction from logging in
                    }

                    if(args[1].equalsIgnoreCase("set")) {
                        if(args.length < 3) {
                            event.getChannel().sendMessage("Incorrect usage").queue();
                            return;
                        } else {
                            if(args[2].equalsIgnoreCase("logchannel")) {

                            }

                            if(args[2].equalsIgnoreCase("role")) {

                            }
                        }
                    }
                }
            }
        }
    }
}
