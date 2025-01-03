package dk.minepay.common;

public interface IMinePayApi {
    static IMinePayApi getInstance() {
        return null;
    }

    String getToken();
}
