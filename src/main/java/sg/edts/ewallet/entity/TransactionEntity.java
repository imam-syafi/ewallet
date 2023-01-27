package sg.edts.ewallet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalTime;

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
    private Double amount;

    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate date;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalTime time;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private Double balanceBefore;

    @Column(nullable = false)
    private Double balanceAfter;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "affected_user_id")
    private UserEntity affectedUser;

    public TransactionEntity() {
    }

    public TransactionEntity(Double amount,
                             Type type,
                             Double balanceBefore,
                             Double balanceAfter,
                             UserEntity affectedUser) {
        this.amount = amount;
        this.type = type;
        this.status = Status.SETTLED;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        this.affectedUser = affectedUser;
    }

    public enum Status {
        PENDING, SETTLED;
    }

    public enum Type {
        TOP_UP,
        TRANSFER_IN,
        TRANSFER_OUT,
        TAX;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Type getType() {
        return type;
    }

    public Status getStatus() {
        return status;
    }

    public Double getBalanceBefore() {
        return balanceBefore;
    }

    public Double getBalanceAfter() {
        return balanceAfter;
    }

    public UserEntity getAffectedUser() {
        return affectedUser;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
