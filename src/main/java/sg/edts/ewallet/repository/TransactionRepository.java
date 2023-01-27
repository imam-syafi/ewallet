package sg.edts.ewallet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sg.edts.ewallet.entity.TransactionEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByDate(LocalDate date);
}
