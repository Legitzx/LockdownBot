package core;

import commands.LoginCommand;
import commands.LogoutCommand;
import commands.RegisterCommand;
import commands.ServerCommands;
import database.DatabaseApi;
import events.ServerJoinEvent;
import managers.AuthenticationManager;
import models.LockdownServerConverter;
import models.LockdownUserConverter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import utils.Config;

import javax.security.auth.login.LoginException;

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

    // Managers
    private AuthenticationManager authenticationManager;

    // JDA
    private JDA api;

    public Bot() throws LoginException, InterruptedException {
        // Setup config
        config = new Config();

        // Setup up JDA
        api = JDABuilder.createDefault(config.get("TOKEN"))
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(new RegisterCommand(this))
                .addEventListeners(new LoginCommand(this))
                .addEventListeners(new LogoutCommand(this))
                .addEventListeners(new ServerJoinEvent(this))
                .addEventListeners(new ServerCommands(this))
                .build().awaitReady();

        // Initialize Dependencies
        databaseApi = new DatabaseApi(this);
        serverConverter = new LockdownServerConverter();
        userConverter = new LockdownUserConverter();
        authenticationManager = new AuthenticationManager(this);

        // TODO: When the bot turns on, automatically set everyones isLoggedIn booleans to false on Mongo
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

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public JDA getJDA() {
        return api;
    }

    public static void main(String[] args) throws LoginException, InterruptedException {
        new Bot();
    }
}