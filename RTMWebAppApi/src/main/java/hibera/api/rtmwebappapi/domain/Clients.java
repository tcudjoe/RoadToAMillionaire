package hibera.api.rtmwebappapi.domain;

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
@Table(name = "clients")
public class Clients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;
    private String client_name;
    private String client_email;
    private String client_phonennumber;
    private String kvk_number;
    private String vat_number;
    private LocalDateTime client_creation_date;
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false) // Foreign key column
    private Company company;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public String getClient_phonennumber() {
        return client_phonennumber;
    }

    public void setClient_phonennumber(String client_phonennumber) {
        this.client_phonennumber = client_phonennumber;
    }

    public String getKvk_number() {
        return kvk_number;
    }

    public void setKvk_number(String kvk_number) {
        this.kvk_number = kvk_number;
    }

    public String getVat_number() {
        return vat_number;
    }

    public void setVat_number(String vat_number) {
        this.vat_number = vat_number;
    }

    public LocalDateTime getClient_creation_date() {
        return client_creation_date;
    }

    public void setClient_creation_date(LocalDateTime client_creation_date) {
        this.client_creation_date = client_creation_date;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
