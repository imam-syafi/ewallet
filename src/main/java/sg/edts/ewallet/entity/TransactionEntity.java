package sg.edts.ewallet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import sg.edts.ewallet.dto.request.SendBalanceDto;
import sg.edts.ewallet.dto.response.BalanceSentDto;

import java.lang.annotation.Target;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
//    private String originUsername;

//    @Column(nullable = false)
//    private String destinationUsername;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Long amount;

//    @Column(nullable = false)
//    private TransactionType type;

//    @Column(nullable = false)
//    @Temporal(TemporalType.DATE)
//    private Date date;
//
//    @Column(nullable = false)
//    @Temporal(TemporalType.TIME)
//    private Date time;

    @Column(nullable = false)
    private TransactionStatus status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


    public TransactionEntity() {
    }

    public TransactionEntity(String username, Long amount, TransactionStatus status) {
        this.username = username;
        this.amount = amount;
        this.status = status;
    }

    //    public TransactionEntity(String originUsername,
//                             String destinationUsername,
//                             Long amount,
//                             TransactionType type,
//                             TransactionStatus status) {
//        this.originUsername = originUsername;
//        this.destinationUsername = destinationUsername;
//        this.amount = amount;
//        this.type = type;
//        this.status = status;
//    }

    public Long getId() {
        return id;
    }

//    public String getOriginUsername() {
//        return originUsername;
//    }

//    public String getDestinationUsername() {
//        return destinationUsername;
//    }

    public Long getAmount() {
        return amount;
    }

//    public TransactionType getType() {
//        return type;
//    }

    public TransactionStatus getStatus() {
        return status;
    }

    public BalanceSentDto toBalanceSentDto(String destinationUsername) {
//        return new BalanceSentDto(id, originUsername, destinationUsername, amount, status);
        return new BalanceSentDto(id, username, destinationUsername, amount, status);
    }

//    public static TransactionEntity from(SendBalanceDto dto, TransactionType type, TransactionStatus status) {
//        return new TransactionEntity(dto.username(), dto.destinationUsername(), dto.amount(), type, status);
//    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }
}
