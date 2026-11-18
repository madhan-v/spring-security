package com.arc.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    //@PreAuthorize("hasAuthority('WEATHER_READ')")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')") // Allow both ADMIN and USER roles to access this endpoint
    public String hello() {
        return "Hello, World!";
    }

    @PostMapping("/post")
    @PreAuthorize("hasAuthority('WEATHER_WRITE')")
    public String postMethod() {
        return "POST method called";
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('WEATHER_DELETE')")
    public String deleteMethod() {
        return "DELETE method called";
    }

    @GetMapping("/name")
    @PostAuthorize("returnObject == 'Madhan'") // Only allow access if the returned value is "Madhan"
    public String giveMeAName() {
        return "Madhan";
    }
    //Try for @PreFilter and @PostFilter
}
