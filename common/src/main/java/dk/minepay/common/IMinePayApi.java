package dk.minepay.common;

@SuppressWarnings("SameReturnValue")
public interface IMinePayApi {
    static IMinePayApi getInstance() {
        return null;
    }

    static IMinePayApi initApi(Object javaPlugin) {
        return null;
    }

    String getToken();
}
