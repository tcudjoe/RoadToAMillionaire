package hibera.api.rtmwebappapi.token;

import hibera.api.rtmwebappapi.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue
    private Long id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType type;
    private Boolean expired;
    private Boolean revoked;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
