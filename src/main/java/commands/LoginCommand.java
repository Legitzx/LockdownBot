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
public class LoginCommand extends ListenerAdapter {
    private Bot bot;

    public LoginCommand(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(bot.getConfig().get("PREFIX") + "login")) {
            if(args.length < 2) {
                event.getChannel().sendMessage("Proper Usage: ``!login <password>``").queue();
                return;
            }

            String memberId = event.getAuthor().getId();
            String password = args[1];

            // Check if user is already logged in
            if(bot.getAuthenticationManager().isLoggedIn(memberId) == true) {
                event.getChannel().sendMessage("You are already logged in!").queue();
                return;
            }

            // Get user from database
            LockdownUser user = bot.getDatabaseApi().getLockdownUser(memberId);

            // if the user does not exist
            if(user == null) {
                event.getChannel().sendMessage("Please register before logging in! How to register: ``!register <password>``").queue();
                return;
            }

            // Compare password
            if(user.getPassword().equals(password)) {
                // Correct credentials
                bot.getAuthenticationManager().login(user);
                event.getChannel().sendMessage("Successful Login").queue();
            } else {
                // Wrong password
                event.getChannel().sendMessage("Incorrect Credentials").queue();
                return;
            }
        }
    }
}
