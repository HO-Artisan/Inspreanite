package ho.artisan.inspreanite.api.utils;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ho.artisan.inspreanite.Inspreanite.NAME;

public class IrnLogger {
    public static final Logger LOGGER = LoggerFactory.getLogger("inspreanite");

    public static void logInfo(@NotNull String message) {
        
            LOGGER.info("[" + NAME + "] " + message);
    }

    public static void logWarn(@NotNull String message) {
        
            LOGGER.warn("[" + NAME + "] " + message);
    }

    public static void logError(@NotNull String message) {
        
            LOGGER.error("[" + NAME + "] " + message);
    }

    public static void logError(@NotNull String message, @NotNull Throwable throwable) {
        
            LOGGER.error("[" + NAME + "] " + message, throwable);
    }

    public static void logDebug(@NotNull String message) {
        
            LOGGER.debug("[" + NAME + "] " + message);
    }

    public static void logDebug(@NotNull String message, @NotNull Throwable throwable) {
        
            LOGGER.debug("[" + NAME + "] " + message, throwable);
    }

    public static void logTrace(@NotNull String message) {
        
            LOGGER.trace("[" + NAME + "] " + message);
    }

    public static void logTrace(@NotNull String message, @NotNull Throwable throwable) {
        
            LOGGER.trace("[" + NAME + "] " + message, throwable);
    }

    public static void logDebugAndError(@NotNull String message) {
        logDebug(message);
        logError(message);
    }

    public static void logDebugAndError(@NotNull String message, @NotNull Throwable throwable) {
        logDebug(message, throwable);
        logError(message);
    }
}
