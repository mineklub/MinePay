package dk.minepay.common;

/**
 * The interface IMinePayApi provides methods to interact with the MinePay API. It allows for
 * initialization and retrieval of the API instance and token.
 */
@SuppressWarnings("SameReturnValue")
public interface IMinePayApi {
    /**
     * Gets the instance of the IMinePayApi.
     *
     * @return the instance of IMinePayApi
     */
    static IMinePayApi getInstance() {
        return null;
    }

    /**
     * Initializes the API with the given Java plugin.
     *
     * @param javaPlugin the Java plugin
     * @return the IMinePayApi instance
     */
    static IMinePayApi initApi(Object javaPlugin) {
        return null;
    }

    /**
     * Gets the token.
     *
     * @return the token
     */
    String getToken();
}
