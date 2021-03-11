package core;

import database.DatabaseApi;
import net.dv8tion.jda.api.JDABuilder;
import utils.Config;

import javax.security.auth.login.LoginException;

/**
 * Contributor(s): Luciano K
 * Description: Main class
 */
public class Bot {
    // Dependencies
    private Config config;
    private DatabaseApi databaseApi;

    public Bot() throws LoginException {
        // Initialize Dependencies
        config = new Config();
        databaseApi = new DatabaseApi(this);

        JDABuilder builder = JDABuilder.createLight(config.get("TOKEN"));
        builder.build();
    }

    public Config getConfig() {
        return config;
    }

    public DatabaseApi getDatabaseApi() {
        return databaseApi;
    }

    public static void main(String[] args) throws LoginException {
        new Bot();
    }
}
