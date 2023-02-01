package sg.edts.ewallet.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sg.edts.ewallet.dto.BalanceSummary;
import sg.edts.ewallet.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    boolean existsByUsername(String username);

    boolean existsByKtp(String ktp);

    Optional<UserEntity> findByUsername(String username);

    @Query(
            nativeQuery = true,
            value = """
                    SELECT
                        trx_summary.username AS username,
                        old_trx.balance_before AS yesterdayBalance,
                        new_trx.balance_after AS currentBalance
                    FROM (
                        SELECT
                            u.id AS user_id,
                            u.username,
                            MIN(filtered_trx.id) AS first_trx_id,
                            MAX(filtered_trx.id) AS last_trx_id
                        FROM users u
                        LEFT JOIN(
                            SELECT *
                            FROM transactions trx
                            WHERE trx.date = ?1
                        ) filtered_trx
                        ON u.id = filtered_trx.affected_user_id
                        GROUP BY u.username
                    ) trx_summary
                    LEFT JOIN transactions old_trx ON trx_summary.first_trx_id = old_trx.id
                    LEFT JOIN transactions new_trx ON trx_summary.last_trx_id = new_trx.id
                    ORDER BY trx_summary.user_id;
                    """
    )
    List<BalanceSummary> generateBalanceSummary(LocalDate date);
}
