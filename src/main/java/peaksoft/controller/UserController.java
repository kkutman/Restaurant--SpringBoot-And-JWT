package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.AuthenticateRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.services.UserServices;

import java.util.List;

/**
 * name : kutman
 **/
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServices userServices;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticateRequest request) {
        return userServices.login(request);
    }

    @PostMapping("/save")
    public UserResponse save(@RequestBody UserRequest userRequest) {
        return userServices.saveUser(userRequest);
    }

    @GetMapping("/get/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userServices.getUserById(id);
    }

    @GetMapping("/getAll")
    public List<UserResponse> getAll() {
        return userServices.getAllUser();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse update(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userServices.updateUser(id, userRequest);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable Long id){
        return userServices.deleteUser(id);
    }
}
