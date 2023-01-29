package sg.edts.ewallet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import sg.edts.ewallet.common.Constant;

import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private Integer failedRetryCount;

    @OneToMany(mappedBy = "affectedUser")
    private List<TransactionEntity> transactions;

    private String ktp;

    public UserEntity() {
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0.0;
        this.failedRetryCount = 0;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public boolean isBanned() {
        return failedRetryCount >= Constant.MAX_RETRY;
    }

    public void incrementFailedRetryCount() {
        failedRetryCount++;
    }

    public void resetFailedRetryCount() {
        failedRetryCount = 0;
    }

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }
}
