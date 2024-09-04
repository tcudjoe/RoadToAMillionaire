package hibera.api.rtmwebappapi.domain;

import hibera.api.rtmwebappapi.users.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank_transactions")
public class BankTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transaction_id;
    private double transaction_amount;
    private String transaction_valuta;
    private LocalDateTime transaction_date;
    private String transaction_description;
    private String transaction_category;
    private String transaction_type;
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
