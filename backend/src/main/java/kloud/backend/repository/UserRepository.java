package kloud.backend.repository;

import kloud.backend.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneById(Long id);

    Optional<User> findOneByIdAndPassword(Long id, String password);

    Optional<User> findOneByLoginAndPassword(String login, String password);
}
