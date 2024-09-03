package hibera.api.rtmwebappapi.domain;

import hibera.api.rtmwebappapi.domain.enums.InvoiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoices")
public class invoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoice_id;
    private String invoice_number;
    private String invoice_date;
    private double invoice_amount;
    private double invoice_vat_amount;
    private InvoiceType invoice_type;
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false) // Foreign key column
    private Company company;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false) // Foreign key column
    private Clients client;
}
