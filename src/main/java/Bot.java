import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

/**
 * Contributor(s): Luciano K
 * Description:
 */
public class Bot {
    public Bot() throws LoginException {
        JDABuilder builder = JDABuilder.createLight("ODE3NTE2MDcyMjQwMDIxNTE0.YEKpLA.I_zA3Wc-gYb2Q83Ep9pt51i3C-E");

        builder.build();
    }

    public static void main(String[] args) throws LoginException {
        new Bot();
    }
}
