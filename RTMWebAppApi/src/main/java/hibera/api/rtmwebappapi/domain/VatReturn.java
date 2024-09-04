package hibera.api.rtmwebappapi.domain;

import hibera.api.rtmwebappapi.domain.enums.ReturnStatus;
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
@Table(name = "vat_return")
public class VatReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long return_id;
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    private LocalDateTime return_period_start;
    private LocalDateTime return_period_end;
    private double vat_to_be_paid;
    private double vat_to_be_returned;
    private ReturnStatus return_status;
    private LocalDateTime return_date;

    public long getReturn_id() {
        return return_id;
    }

    public void setReturn_id(long return_id) {
        this.return_id = return_id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public LocalDateTime getReturn_period_start() {
        return return_period_start;
    }

    public void setReturn_period_start(LocalDateTime return_period_start) {
        this.return_period_start = return_period_start;
    }

    public LocalDateTime getReturn_period_end() {
        return return_period_end;
    }

    public void setReturn_period_end(LocalDateTime return_period_end) {
        this.return_period_end = return_period_end;
    }

    public double getVat_to_be_paid() {
        return vat_to_be_paid;
    }

    public void setVat_to_be_paid(double vat_to_be_paid) {
        this.vat_to_be_paid = vat_to_be_paid;
    }

    public double getVat_to_be_returned() {
        return vat_to_be_returned;
    }

    public void setVat_to_be_returned(double vat_to_be_returned) {
        this.vat_to_be_returned = vat_to_be_returned;
    }

    public ReturnStatus getReturn_status() {
        return return_status;
    }

    public void setReturn_status(ReturnStatus return_status) {
        this.return_status = return_status;
    }

    public LocalDateTime getReturn_date() {
        return return_date;
    }

    public void setReturn_date(LocalDateTime return_date) {
        this.return_date = return_date;
    }
}
