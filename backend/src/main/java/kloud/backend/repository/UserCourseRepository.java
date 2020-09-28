package kloud.backend.repository;

import kloud.backend.entity.User;
import kloud.backend.entity.UserCourse;
import kloud.backend.service.dto.UserCourseDTO;
import kloud.backend.service.dto.UserCourseDTO2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {


//    List<UserCourseDTO> selAllUsersByCourseId(Long courseId);


//    @Query(name = "selAllUsersByCourseId", value = "select u.id as id, u.login as login, u.real_name as realName, u.email as email from saas_user u where u.id in (select r.student_id from saas_users_courses r where r.course_id = ?1)", nativeQuery = true)
    @Query(value = "select u.id as id, u.login as login, u.realName as realName, u.email as email from User u, UserCourse uc where uc.courseId=?1 and u.id=uc.studentId")
    List<UserCourseDTO> selAllUsersByCourseId(Long courseId);

    @Query(value = "select u.id as id, u.login as login, u.real_name as realName from saas_user u where u.user_type = 0 and u.id not in (select r.student_id from saas_users_courses r where r.course_id = ?1)", nativeQuery = true)
    List<UserCourseDTO2> selAllUsersNotInCourse(Long courseId);


    void deleteAllByCourseIdAndStudentIdIn(Long courseId, List<Long> studentId);




}
