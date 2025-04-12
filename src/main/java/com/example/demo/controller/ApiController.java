package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ApiController {

    // 🟢 Public API - open to everyone
    @GetMapping("/api/public/hello")
    public String helloPublic() {
        return "Hello, public user";
    }

    // 🔒 Protected API - for USER or ADMIN
    @GetMapping("/api/user/hello")
    public String helloUser(Principal principal) {
        return "Hello, user: " + principal.getName();
    }

    // 🔒 Protected API - for ADMIN only
    @GetMapping("/api/admin/hello")
    public String helloAdmin(Principal principal) {
        return "Hello, admin: " + principal.getName();
    }
}
