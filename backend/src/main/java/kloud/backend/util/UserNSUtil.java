package kloud.backend.util;

import java.util.Optional;

//Convert between User ID and K8s Namespace
public class UserNSUtil {
    private static final String PREFIX = "kusr";

    public static String toNS(String id) {
        if (id == null) {
            return null;
        }
        return PREFIX + id;
    }

    public static Optional<String> toUID(String namespace) {
        if (namespace.startsWith(PREFIX)) {
            return Optional.of(namespace.replaceFirst(PREFIX, ""));
        }
        return Optional.empty();
    }
}
