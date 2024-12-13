package com.example.WebSocket_Chat.login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private RepoLogin repoLogin;

    @PostMapping("/login")
    public ResponseEntity<Login> login(@RequestBody Login login) {
       Login checkLogin= repoLogin.findByUsername(login.getUsername());
       if(checkLogin.getPassword().equals(login.getPassword())) {
           return ResponseEntity.ok(checkLogin);
       }
       return ResponseEntity.noContent().build();

    }
}
