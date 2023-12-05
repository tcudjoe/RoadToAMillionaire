package hibera.api.rtmwebappapi.controllers.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

//    @GetMapping("")
//    public ResponseEntity<String> hello(String string){
//        return ResponseEntity.ok("Hello from secure endpoint!");
//    }

    @GetMapping("")
    public String get(){
        return "GET:: user controller";
    }

    @PostMapping("")
    public String post(){
        return "POST:: user controller";
    }

    @PutMapping("")
    public String put(){
        return "PUT:: user controller";
    }

    @DeleteMapping("")
    public String delete(){
        return "DELETE:: user controller";
    }
}
