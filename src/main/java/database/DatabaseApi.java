package database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.lang.Nullable;
import core.Bot;
import models.LockdownServer;
import models.LockdownUser;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Contributor(s): Luciano K
 * Description: Interacts with MongoDB
 */
public class DatabaseApi extends DatabaseConnection {
    // Main bot
    private Bot bot;

    // Gets main collection
    private MongoCollection serverCollection;
    private MongoCollection userCollection;

    public DatabaseApi(Bot bot) {
        super(bot);
        this.bot = bot;
        serverCollection = super.getDatabase().getCollection(bot.getConfig().get("server_collection"));
        userCollection = super.getDatabase().getCollection(bot.getConfig().get("user_collection"));
    }

    /**
     * Creates a LockdownUser in the MongoDB.
     * @param user      User you wish to create.
     */
    public void createLockdownUser(LockdownUser user) {
        userCollection.insertOne(new Document(bot.getUserConverter().serialize(user)));
    }

    /**
     * Creates a LockdownServer in the MongoDB.
     * @param server
     */
    public void createLockdownServer(LockdownServer server) {
        serverCollection.insertOne(new Document(bot.getServerConverter().serialize(server)));
    }

    /**
     * Gets a LockdownServer from the MongoDB.
     * @param id        ID of the server you wish to get.
     * @return          LockdownServer or null (if server does not exist).
     */
    @Nullable
    public LockdownServer getLockdownServer(String id) {
        try {
            Document found = (Document) serverCollection.find(new Document("id", id)).first();

            return bot.getServerConverter().deserialize(found);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Gets a LockdownUser from the MongoDB.
     * @param id        ID of the user you wish to get.
     * @return          LockdownUser or null (if user does not exist).
     */
    @Nullable
    public LockdownUser getLockdownUser(String id) {
        try {
            Document found = (Document) userCollection.find(new Document("id", id)).first();

            return bot.getUserConverter().deserialize(found);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Updates a LockdownUser.
     * @param user      User you wish to update.
     */
    public void updateLockdownUser(LockdownUser user) {
        userCollection.findOneAndUpdate(new Document("id", user.getId()), new Document("$set", bot.getUserConverter().serialize(user)));
    }

    /**
     * Updates a LockdownServer.
     * @param server    Server you wish to update.
     */
    public void updateLockdownServer(LockdownServer server) {
        serverCollection.findOneAndUpdate(new Document("id", server.getId()), new Document("$set", bot.getServerConverter().serialize(server)));
    }

    /**
     * Returns all the servers that have a users id on their auth list
     * @param id        user id
     * @return          servers that contain the user id
     */
    public List<LockdownServer> getServersThatHaveThisUser(String id) {
        List<LockdownServer> contains = new ArrayList<>();

        FindIterable<Document> iterable = serverCollection.find();
        Iterator it = iterable.iterator();
        while(it.hasNext()) {
            LockdownServer server = bot.getServerConverter().deserialize((Document) it.next());

            if(server.getAuthList().contains(id)) {
                contains.add(server);
            }
        }

        return contains;
    }
}