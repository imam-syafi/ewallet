package sg.edts.ewallet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import sg.edts.ewallet.dto.request.SendBalanceDto;
import sg.edts.ewallet.dto.response.BalanceSentDto;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originUsername;

    @Column(nullable = false)
    private String destinationUsername;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private TransactionType type;

//    @Column(nullable = false)
//    @Temporal(TemporalType.DATE)
//    private Date date;
//
//    @Column(nullable = false)
//    @Temporal(TemporalType.TIME)
//    private Date time;

    @Column(nullable = false)
    private TransactionStatus status;

    public TransactionEntity() {
    }

    public TransactionEntity(String originUsername,
                             String destinationUsername,
                             Long amount,
                             TransactionType type,
                             TransactionStatus status) {
        this.originUsername = originUsername;
        this.destinationUsername = destinationUsername;
        this.amount = amount;
        this.type = type;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getOriginUsername() {
        return originUsername;
    }

    public String getDestinationUsername() {
        return destinationUsername;
    }

    public Long getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public BalanceSentDto toBalanceSentDto() {
        return new BalanceSentDto(id, originUsername, destinationUsername, amount, status);
    }

    public static TransactionEntity from(SendBalanceDto dto, TransactionType type, TransactionStatus status) {
        return new TransactionEntity(dto.username(), dto.destinationUsername(), dto.amount(), type, status);
    }
}
