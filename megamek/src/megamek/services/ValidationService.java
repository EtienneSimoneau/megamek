package megamek.services;

import megamek.MMConstants;
import megamek.common.annotations.Nullable;
import megamek.common.commandline.AbstractCommandLineParser;
import org.apache.logging.log4j.LogManager;

public class ValidationService {
    /**
     *
     * @param serverAddress
     * @return valid hostName
     * @throws AbstractCommandLineParser.ParseException for null or empty serverAddress
     */
    public static String validateServerAddress(String serverAddress) throws AbstractCommandLineParser.ParseException {
        if ((serverAddress == null) || serverAddress.isBlank()) {
            String msg = String.format("serverAddress must not be null or empty");
            LogManager.getLogger().error(msg);
            throw new AbstractCommandLineParser.ParseException(msg);
        }
        return serverAddress.trim();
    }

    /**
     *
     * @param playerName throw ParseException if null or empty
     * @return valid playerName
     */
    public static String validatePlayerName(String playerName) throws AbstractCommandLineParser.ParseException {
        if (playerName == null) {
            String msg = String.format("playerName must not be null");
            LogManager.getLogger().error(msg);
            throw new AbstractCommandLineParser.ParseException(msg);
        }

        if (playerName.isBlank()) {
            String msg = String.format("playerName must not be empty string");
            LogManager.getLogger().error(msg);
            throw new AbstractCommandLineParser.ParseException(msg);
        }

        return playerName.trim();
    }

    /**
     *
     * @param password
     * @return valid password or null if no password or password is blank string
     */
    @Nullable
    public static String validatePassword(@Nullable String password) {
        if ((password == null) || password.isBlank()) return null;
        return password.trim();
    }

    /**
     *
     * @param port if 0 or less, will return default, if illegal number, throws ParseException
     * @return valid port number
     */
    public static int validatePort(int port) throws AbstractCommandLineParser.ParseException {
        if (port <= 0) {
            return MMConstants.DEFAULT_PORT;
        }

        if ((port < MMConstants.MIN_PORT) || (port > MMConstants.MAX_PORT)) {
            String msg = String.format("Port number %d outside allowed range %d-%d", port, MMConstants.MIN_PORT, MMConstants.MAX_PORT);
            LogManager.getLogger().error(msg);
            throw new AbstractCommandLineParser.ParseException(msg);

        }
        return port;
    }
}
