package hibera.api.rtmwebappapi.Auth;

import hibera.api.rtmwebappapi.domain.enums.TokenType;
import hibera.api.rtmwebappapi.users.User;
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
    @Column(unique = true)
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType type;
    private Boolean expired;
    private Boolean revoked;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

}
