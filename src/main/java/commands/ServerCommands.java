package commands;

import core.Bot;
import models.LockdownServer;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

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
                    }
                }
            }
        }
    }
}
