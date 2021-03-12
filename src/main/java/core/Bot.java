package core;

import database.DatabaseApi;
import models.LockdownServer;
import models.LockdownServerConverter;
import models.LockdownUserConverter;
import net.dv8tion.jda.api.JDABuilder;
import utils.Config;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contributor(s): Luciano K
 * Description: Main class
 */
public class Bot {
    // Config
    private Config config;

    // APIs
    private DatabaseApi databaseApi;

    // Converters
    private LockdownServerConverter serverConverter;
    private LockdownUserConverter userConverter;

    public Bot() throws LoginException {
        // Initialize Dependencies
        config = new Config();
        databaseApi = new DatabaseApi(this);
        serverConverter = new LockdownServerConverter();
        userConverter = new LockdownUserConverter();

        JDABuilder builder = JDABuilder.createLight(config.get("TOKEN"));
        builder.build();
    }

    public Config getConfig() {
        return config;
    }

    public DatabaseApi getDatabaseApi() {
        return databaseApi;
    }

    public LockdownServerConverter getServerConverter() {
        return serverConverter;
    }

    public LockdownUserConverter getUserConverter() {
        return userConverter;
    }

    public static void main(String[] args) throws LoginException {
        new Bot();
    }
}