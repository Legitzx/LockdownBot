import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

/**
 * Contributor(s): Luciano K
 * Description:
 */
public class Bot {
    public Bot() throws LoginException {
        JDABuilder builder = JDABuilder.createLight("TOKEN");

        builder.build();
    }

    public static void main(String[] args) throws LoginException {
        new Bot();
    }
}
