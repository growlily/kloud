package kloud.backend.util;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    private static String spring = "春";
    private static String summer = "夏";
    private static String autumn = "秋";

    public static String getCourseSemester() {
        StringBuilder stringBuilder = new StringBuilder();
        YearMonth now = YearMonth.now();
        int year = now.getYear();
        int month = now.getMonth().getValue();
        if(month == 1) {
            stringBuilder.append(year-1);
            stringBuilder.append(autumn);
        }
        else if(month <= 6) {
            stringBuilder.append(year);
            stringBuilder.append(spring);
        }
        else if (month <= 8){
            stringBuilder.append(year);
            stringBuilder.append(summer);
        }
        else {
            stringBuilder.append(year);
            stringBuilder.append(autumn);
        }
        return stringBuilder.toString();
    }

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime parseTime(String text) {
        return LocalDateTime.parse(text, dateTimeFormatter);
    }
}
