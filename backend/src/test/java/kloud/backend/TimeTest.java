package kloud.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class TimeTest {

    @Test
    public void masterTimeUtils() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse("2020-09-21 00:00:00", dateTimeFormatter);
        System.out.println(localDateTime);
        String format = dateTimeFormatter.format(localDateTime);
        System.out.println(format);
    }
}
