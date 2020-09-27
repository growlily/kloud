package kloud.backend.config;

public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    //metrics
    public static final String CPU = "cpu";
    public static final String MEMORY = "memory";
    public static final int MiB = 1024 * 1024;

    private Constants() {

    }
}

