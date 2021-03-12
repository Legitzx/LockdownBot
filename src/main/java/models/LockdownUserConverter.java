package models;

import org.bson.Document;

/**
 * Contributor(s): Luciano K
 * Description: Converts LockdownUser Objects
 */
public class LockdownUserConverter {
    /**
     * Converts MongoDB Documents into LockdownUser Objects.
     * @param document  Document you wish to deserialize.
     * @return          LockdownUser Object.
     */
    public LockdownUser deserialize(Document document) {
        String id = document.getString("id");
        String password = document.getString("password");
        boolean isLoggedIn = document.getBoolean("isLoggedIn");

        return new LockdownUser(id, password, isLoggedIn);
    }

    /**
     * Converts LockdownUser Objects into MongoDB Documents.
     * @param user      User you wish to serialize.
     * @return          MongoDB Document comprised of LockdownUser Data.
     */
    public Document serialize(LockdownUser user) {
        Document document = new Document();

        document.put("id", user.getId());
        document.put("password", user.getPassword());
        document.put("isLoggedIn", user.isLoggedIn());

        return document;
    }
}
