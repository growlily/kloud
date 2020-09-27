package kloud.backend;

import kloud.backend.controller.vm.ChangeInfoVM;
import kloud.backend.controller.vm.ChangePwdVM;
import kloud.backend.entity.Course;
import kloud.backend.entity.User;
import kloud.backend.repository.CourseRepository;
import kloud.backend.repository.UserRepository;
import kloud.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Size;
import java.util.Optional;

@SpringBootTest
public class UserTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    CourseRepository courseRepository;

    @Test
    public void  addTest() {
        User user1 = new User();
        user1.setLogin("17373516");
        user1.setPassword("12345678");
        user1.setEmail("1044599330@qq.com");
        user1.setRealName("敖远超");
        user1.setCreatedBy("测试人员");
        userRepository.save(user1);

        User user2 = new User();
        user2.setLogin("17373189");
        user2.setPassword("12345678");
        user2.setEmail("1572002813@qq.com");
        user2.setRealName("郭荣");
        user2.setCreatedBy("测试人员");
        userRepository.save(user2);

        User user3 = new User();
        user3.setLogin("17373202");
        user3.setPassword("12345678");
        user3.setEmail("1609971397@qq.com");
        user3.setRealName("余帅");
        user3.setCreatedBy("测试人员");
        userRepository.save(user3);
    }

    @Test
    public void addTest2() {
        User user1 = new User();
        user1.setLogin("2020");
        user1.setPassword("12345678");
        user1.setEmail("wssadwdayc@gmail.com");
        user1.setUserType(1);
        user1.setRealName("人民教师");
        user1.setCreatedBy("测试人员");
        userRepository.save(user1);

        User user2 = new User();
        user2.setLogin("0000");
        user2.setPassword("12345678");
        user2.setEmail("wssadwdayc123@gmail.com");
        user2.setUserType(2);
        user2.setRealName("管理员");
        user2.setCreatedBy("测试人员");
        userRepository.save(user2);
    }

    /*
    修改邮箱和密码
     */
    @Test
    @Transactional
    public void updateTest() {
        ChangeInfoVM changeInfoVM = new ChangeInfoVM();
        changeInfoVM.setId(1L);
        changeInfoVM.setEmail("1091390860@qq.com");
        changeInfoVM.setRealName("敖哈哈");
        userService.changeInfo(changeInfoVM).ifPresent(System.out::println);
    }

    /*
    修改密码
     */
    @Test
    @Transactional
    public void updateTest2() {
        ChangePwdVM changePwdVM = new ChangePwdVM();
        changePwdVM.setId(1L);
        changePwdVM.setPassword("ayc1999");
        userService.changePwd(changePwdVM);
    }

    @Test
    public void test3() {
        Long courseId = 1L;
        System.out.println(courseId);
        Course course = courseRepository.findOneById(courseId).get();
        System.out.println(course);
        User user = userRepository.findOneById(course.getTeacherId()).get();
        System.out.println(user);
    }

}
