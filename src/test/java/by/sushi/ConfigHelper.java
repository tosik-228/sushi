package by.sushi;

import org.aeonbits.owner.ConfigFactory;

public class ConfigHelper {
    private final static ApiConfig api = ConfigFactory
            .create(ApiConfig.class, System.getProperties());

    public static String url() {
        return api.host() + ":" + api.port();
    }

    public static String getToken() {return api.getToken();}
}
