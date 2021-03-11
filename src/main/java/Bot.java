import net.dv8tion.jda.api.JDABuilder;
import utils.Config;

import javax.security.auth.login.LoginException;

/**
 * Contributor(s): Luciano K
 * Description:
 */
public class Bot {
    // Dependencies
    private Config config;

    public Bot() throws LoginException {
        // Initialize Dependencies
        config = new Config();

        // Create Bot
        JDABuilder builder = JDABuilder.createLight(config.get("TOKEN"));
        builder.build();
    }

    public Config getConfig() {
        return config;
    }

    public static void main(String[] args) throws LoginException {
        new Bot();
    }
}
