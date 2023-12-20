package hibera.api.rtmwebappapi.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static hibera.api.rtmwebappapi.domain.Permissions.*;

@RequiredArgsConstructor
public enum Role {
    ADMIN(
            Set.of(
                    ADMIN_CREATE,
                    ADMIN_DELETE,
                    ADMIN_UPDATE,
                    ADMIN_READ,
                    MANAGER_UPDATE,
                    MANAGER_CREATE,
                    MANAGER_READ,
                    MANAGER_DELETE,
                    USER_UPDATE,
                    USER_CREATE,
                    USER_READ,
                    USER_DELETE
            )
    ),
    MANAGER(
            Set.of(
                    MANAGER_CREATE,
                    MANAGER_UPDATE,
                    MANAGER_READ,
                    MANAGER_DELETE,
                    USER_UPDATE,
                    USER_CREATE,
                    USER_READ,
                    USER_DELETE
            )
    ),
    USER(
            Set.of(
                    USER_CREATE,
                    USER_UPDATE,
                    USER_READ,
                    USER_DELETE

            )
    );

    @Getter
    private final Set<Permissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
