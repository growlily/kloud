package kloud.backend;

import kloud.backend.entity.UserCourse;
import kloud.backend.repository.UserCourseRepository;
import kloud.backend.service.dto.UserCourseDTO;
import kloud.backend.service.dto.UserCourseDTO2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class UserCourseTest {

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Test
//    @Transactional
    public void test1() {
        UserCourse userCourse = new UserCourse();
        userCourse.setStudentId(2L);
        userCourse.setCourseId(1L);
        userCourse.setCreatedBy("测试人员");
        userCourseRepository.save(userCourse);
    }

    @Test
    public void test2() {
//        List<UserCourseDTO> userCourseDTOS = userCourseRepository.selAllUsersByCourseId(1L);
//        System.out.println(userCourseDTOS);
        List<UserCourseDTO2> userCourseDTO2s = userCourseRepository.selAllUsersNotInCourse(1L);
        System.out.println(userCourseDTO2s);
    }

}
