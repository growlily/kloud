package kloud.backend.util;

//Convert between Owner and K8s Namespace
public class NamespaceUtil {
    private static final String USER_PREFIX = "kusr";
    private static final String COURSE_PREFIX = "kours";

    public static String getNS(String id, String course) {
        if (id == null) {
            return null;
        }
        if (course.equals("0"))
            return USER_PREFIX + id;
        return COURSE_PREFIX + course;
    }

}
