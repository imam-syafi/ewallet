package sg.edts.ewallet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sg.edts.ewallet.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
    boolean existsByKtp(String ktp);
    Optional<UserEntity> findByUsername(String username);
}
