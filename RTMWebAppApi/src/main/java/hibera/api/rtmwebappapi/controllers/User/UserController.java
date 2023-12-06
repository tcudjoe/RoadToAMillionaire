package hibera.api.rtmwebappapi.controllers.User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
public class UserController {

//    @GetMapping("")
//    public ResponseEntity<String> hello(String string){
//        return ResponseEntity.ok("Hello from secure endpoint!");
//    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read', 'manager:read')")
    public String get(){
        return "GET:: user controller";
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('admin:create', 'user:create', 'manager:create')")
    public String post(){
        return "POST:: user controller";
    }

    @PutMapping("")
    @PreAuthorize("hasAnyAuthority('admin:update', 'user:update', 'manager:update')")
    public String put(){
        return "PUT:: user controller";
    }

    @DeleteMapping("")
    @PreAuthorize("hasAnyAuthority('admin:delete', 'user:delete', 'manager:delete')")
    public String delete(){
        return "DELETE:: user controller";
    }
}
