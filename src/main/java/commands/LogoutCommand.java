package commands;

import core.Bot;
import models.LockdownUser;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * Contributor(s): Luciano K
 * Description:
 */
public class LogoutCommand extends ListenerAdapter {
    private Bot bot;

    public LogoutCommand(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(bot.getConfig().get("PREFIX") + "logout")) {
            String memberId = event.getAuthor().getId();

            // Make sure user is not logged out already
            if(bot.getAuthenticationManager().isLoggedIn(memberId) == false) {
                event.getChannel().sendMessage("You are already logged out!").queue();
                return;
            }

            // Get user from database
            LockdownUser user = bot.getDatabaseApi().getLockdownUser(memberId);

            // Check if user exists
            if(user == null) {
                event.getChannel().sendMessage("You are not registered yet! Create an account by using ``!register <password>``").queue();
                return;
            }

            // logout user
            bot.getAuthenticationManager().logout(user);
            event.getChannel().sendMessage("Logout successful!").queue();
        }
    }
}
