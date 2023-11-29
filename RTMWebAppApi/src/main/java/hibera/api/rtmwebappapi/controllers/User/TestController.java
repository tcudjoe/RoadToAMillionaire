package hibera.api.rtmwebappapi.controllers.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping("")
    public ResponseEntity<String> hello(String string){
        return ResponseEntity.ok("Hello from secure endpoint!");
    }
}
