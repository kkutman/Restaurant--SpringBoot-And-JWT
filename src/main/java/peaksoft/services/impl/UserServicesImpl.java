package peaksoft.services.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.JwtServices;
import peaksoft.dto.request.AuthenticateRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exception.SaveUserException;
import peaksoft.repository.UserRepository;
import peaksoft.services.UserServices;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtServices;
    private final AuthenticationManager authenticationManager;


    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        if (userRequest.getFirstName().isBlank() ||
                userRequest.getLastName().isBlank() ||
                userRequest.getDateOfBirth() == null ||
                userRequest.getEmail().isBlank() ||
                userRequest.getPassword().isBlank() ||
                userRequest.getPhoneNumber().isBlank() ||
                userRequest.getRole() == null ||
                userRequest.getExperience() < 0) {
            return null;
        }
        if (userRequest.getRole().equals(Role.CHEF)) {
            if (userRequest.getExperience() < 2) {
                try {
                    throw new SaveUserException("The chef must have experience pain 2");
                } catch (SaveUserException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (userRequest.getRole().equals(Role.WAITER)) {
            if (userRequest.getExperience() < 1) {
                try {
                    throw new SaveUserException("The waiter must have experience pain 1");
                } catch (SaveUserException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        User user = new User(
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getDateOfBirth(),
                userRequest.getEmail(),
                passwordEncoder.encode(userRequest.getPassword()),
                userRequest.getPhoneNumber(),
                userRequest.getRole(),
                userRequest.getExperience()
        );
        userRepository.save(user);
        var jwtToken = jwtServices.generateToken(user);
        System.out.println(jwtToken);
        return new UserResponse(
                user.getId(),
                user.getFirstName().concat(userRequest.getLastName()),
                user.getDateOfBirth(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );
    }

    @Override
    public AuthenticationResponse login(AuthenticateRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtServices.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("Author with email :%s already exists", id)));
        return new UserResponse(
                user.getId(),
                user.getFirstName().concat(user.getLastName()),
                user.getDateOfBirth(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );
    }

    @Override
    public List<UserResponse> getAllUser() {
        return userRepository.getAllResponse();
    }

    @Override
    public String deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("Author with email :%s already exists", id)));
        userRepository.deleteById(id);
        return user.getEmail() + " is deleted!";
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("Author with email :%s already exists", id)));
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());
        user.setExperience(userRequest.getExperience());
        userRepository.save(user);
        return new UserResponse(
                user.getId(),
                user.getFirstName().concat(user.getLastName()),
                user.getDateOfBirth(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );
    }

//    @PostConstruct
//    public void init() {
//        UserRequest user = new UserRequest(
//                "admin",
//                "admin",
//                LocalDate.now(),
//                "admin",
//                "admin",
//                "+996777666555",
//                Role.ADMIN,
//                2
//        );
//        saveUser(user);
//    }
}
