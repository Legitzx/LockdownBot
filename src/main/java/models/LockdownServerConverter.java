package models;

import org.bson.Document;

import java.util.List;

/**
 * Contributor(s): Luciano K
 * Description: Converts LockdownServer Objects
 */
public class LockdownServerConverter {
    /**
     * Converts MongoDB Documents into LockdownServer Objects.
     * @param document      Documents you wish to deserialize.
     * @return              LockdownServer Object.
     */
    public LockdownServer deserialize(Document document) {
        String id = document.getString("id");
        List<String> authList = document.getList("authList", String.class);
        boolean status = document.getBoolean("status");
        String idLogChannel = document.getString("idLogChannel");
        String idRole = document.getString("idRole");

        return new LockdownServer(id, authList, status, idLogChannel, idRole);
    }

    /**
     * Converts LockdownServer Objects into MongoDB Documents.
     * @param server        Server you wish to serialize.
     * @return              MongoDB Document comprised of LockdownServer Data.
     */
    public Document serialize(LockdownServer server) {
        Document document = new Document();

        document.put("id", server.getId());
        document.put("authList", server.getAuthList());
        document.put("status", server.isStatus());
        document.put("idLogChannel", server.getIdLogChannel());
        document.put("idRole", server.getIdRole());

        return document;
    }
}
