package utils;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Contributor(s): Luciano K
 * Description: Loader class for the environment variables
 */
public class Config {
    // Loads .env library
    private final Dotenv dotenv = Dotenv.load();

    /**
     * Gets values from the .env file
     * @param key       key to search for in the .env file
     * @return          corresponding value
     */
    public String get(String key) {
        return dotenv.get(key.toUpperCase());
    }
}
