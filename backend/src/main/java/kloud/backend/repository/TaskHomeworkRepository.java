package kloud.backend.repository;

import kloud.backend.entity.TaskHomework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskHomeworkRepository extends JpaRepository<TaskHomework, Long> {
}
