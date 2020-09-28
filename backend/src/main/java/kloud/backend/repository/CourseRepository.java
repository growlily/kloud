package kloud.backend.repository;

import kloud.backend.entity.Course;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByTeacherId(Long id);

    void deleteById(Long id);

    @Cacheable(value = "courseRepositoryForTaskUseOnly", key = "#id")
    Optional<Course> findOneById(Long id);

}
