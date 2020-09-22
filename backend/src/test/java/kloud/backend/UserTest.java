package kloud.backend;

import kloud.backend.entity.User;
import kloud.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    @Autowired
    UserRepository userRepository;

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

}
