package g60283.dev.meteo.model;

/**
 * Custom exception class for handling errors in the Weather API fetching process.
 * This exception is thrown when there is a problem during API requests or data parsing.
 */
public class WeatherException extends RuntimeException {
    /**
     * Constructs a new WeatherException with a specific message and cause.
     *
     * @param message the detail message describing the reason for the exception
     * @param cause the cause of the exception (a throwable cause that led to this exception)
     */
    public WeatherException(String message, Throwable cause) {
        super(message, cause);
    }
}
