package database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import core.Bot;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contributor(s): Luciano K
 * Description: Connects to MongoDB Database
 */
public class DatabaseConnection {
    private MongoDatabase database;

    /**
     * Description: Connects to remote MongoDB Database.
     */
    public DatabaseConnection(Bot bot) {
        MongoClient mongoClient = MongoClients.create(
                bot.getConfig().get("uri"));
        database = mongoClient.getDatabase(bot.getConfig().get("database"));
        muteLogger();
    }

    /**
     * Description: Prevents console spam due to MongoDB Logging.
     */
    private void muteLogger() {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
