package commands;

import core.Bot;
import models.LockdownUser;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * Contributor(s): Luciano K
 * Description: Registers a user
 */
public class RegisterCommand extends ListenerAdapter {
    private Bot bot;

    public RegisterCommand(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(bot.getConfig().get("PREFIX") + "register")) {
            if(args.length < 2) {
                event.getChannel().sendMessage("Usage: ``!register <password>``").queue();
                return;
            }

            String memberId = event.getAuthor().getId();
            String password = args[1];

            if(password.length() > 0 && password.length() <= 32) {
                // Check if user is already registered
                LockdownUser user = bot.getDatabaseApi().getLockdownUser(memberId);

                if(user != null) {
                    event.getChannel().sendMessage("You are already registered!").queue();
                    return;
                }
                // If not register user
                bot.getDatabaseApi().createLockdownUser(new LockdownUser(memberId, password, false));

                event.getChannel().sendMessage("Successfully Registered!").queue();
            } else {
                event.getChannel().sendMessage("Password must be between 0 and 32 characters!").queue();
            }
        }
    }
}