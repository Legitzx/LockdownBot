package database;

import com.mongodb.client.MongoCollection;
import core.Bot;

/**
 * Contributor(s): Luciano K
 * Description:
 */
public class DatabaseApi extends DatabaseConnection {
    // Gets main collection
    private MongoCollection collection;

    public DatabaseApi(Bot bot) {
        super(bot);
        collection = super.getDatabase().getCollection(bot.getConfig().get("collection"));
    }
}
