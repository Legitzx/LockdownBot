package events;

import core.Bot;
import models.LockdownServer;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Contributor(s): Luciano K
 * Description:
 */
public class ServerJoinEvent extends ListenerAdapter {
    private Bot bot;

    public ServerJoinEvent(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        String id = event.getGuild().getId();

        if(bot.getDatabaseApi().getLockdownServer(id) == null) {
            LockdownServer server = new LockdownServer(id, new ArrayList<>(), true, "", "");
            bot.getDatabaseApi().createLockdownServer(server);
        }

        event.getGuild().getOwner().getUser().openPrivateChannel().queue((channel) -> {
            channel.sendMessage("Thanks for inviting the LockdownBot! Please refer to the ``README.md`` for command usage! https://github.com/Legitzx/LockdownBot/blob/main/README.md").queue();
        });
    }
}
