package peaksoft.services.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import peaksoft.config.JwtServices;
import peaksoft.dto.request.AuthenticateRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exception.BadRequestException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.UserRepository;
import peaksoft.services.UserServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtServices;
    private final AuthenticationManager authenticationManager;


    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        try {
            if (userRequest.getFirstName().isBlank() &&
                    userRequest.getLastName().isBlank() &&
                    userRequest.getDateOfBirth() == null &&
                    userRequest.getEmail().isBlank() &&
                    !userRequest.getEmail().contains("@") &&
                    userRequest.getPassword().isBlank() &&
                    userRequest.getPhoneNumber().isBlank() &&
                    userRequest.getRole() == null &&
                    userRequest.getExperience() < 0) {
                throw new BadRequestException("save Exception");
            }
            if (userRequest.getRole().equals(Role.CHEF)) {
                if (userRequest.getExperience() < 2) {
                    throw new BadRequestException("The chef must have experience pain 2");
                }
                int now = LocalDate.now().getYear();
                int dateOfBirth = userRequest.getDateOfBirth().getYear();
                int num = now - dateOfBirth;
                if (num > 46 || num < 24) {
                    throw new BadRequestException("The chef age > 25 > 45 ");
                }
            }
            if (userRequest.getRole().equals(Role.WAITER)) {
                if (userRequest.getExperience() < 1) {
                    throw new BadRequestException("The waiter must have experience pain 1");
                }
                int now = LocalDate.now().getYear();
                int dateOfBirth = userRequest.getDateOfBirth().getYear();
                int num = now - dateOfBirth;
                if (num > 31 || num < 17) {
                    throw new BadRequestException("The waiter age > 18 > 30");
                }
            }
            if (!userRequest.getPhoneNumber().startsWith("+996")) {
                if (userRequest.getPhoneNumber().length() != 13) {
                    throw new BadRequestException("Phone number exception");
                }
            }
            if (userRepository.findAll().stream().count() != 0) {
                for (User user : userRepository.findAll()) {
                    if (userRequest.getEmail().equals(user.getEmail())) {
                        throw new BadRequestException("Email exception");
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
                    LocalDate.now().minusYears(user.getDateOfBirth().getYear()).getYear(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getRole()
            );
        }catch (RuntimeException r){
            throw new BadRequestException("email");
        }
    }


    @Override
    public AuthenticationResponse login(AuthenticateRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user1 = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtServices.generateToken(user1);
        return AuthenticationResponse.builder()
                .token(jwtToken).id(user1.getId()).build();
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Author with email :%s already exists", id)));
        return new UserResponse(
                user.getId(),
                user.getFirstName().concat(user.getLastName()),
                LocalDate.now().minusYears(user.getDateOfBirth().getYear()).getYear(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<UserResponse>userResponseList  = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            for (UserResponse userResponse : userRepository.getAllResponse()) {
                if(userResponse.getId()==user.getId()){
                    userResponse.setDateOfBirth(LocalDate.now().minusYears(user.getDateOfBirth().getYear()).getYear());
                    userResponseList.add(userResponse);
                }
            }
        }
        return userResponseList;
    }

    @Override
    public String deleteUser(Long id) {
        if(id!=1L) {
            User user = userRepository.findById(id).orElseThrow(() ->
                    new NotFoundException(String.format("Author with email :%s already exists", id)));
            userRepository.deleteById(id);
            return user.getEmail() + " is deleted!";
        }else {
            throw new BadRequestException("user by id 1L dont deleted");
        }

    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Author with email :%s already exists", id)));
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
                LocalDate.now().minusYears(user.getDateOfBirth().getYear()).getYear(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );
    }

    @PostConstruct
    public void init() {
        UserRequest user = new UserRequest(
                "admin",
                "admin",
                LocalDate.now(),
                "admin@gmail.com",
                "admin",
                "+996777666555",
                Role.ADMIN,
                2
        );
        long count = getAllUser().stream().count();
        if(count==0){
            saveUser(user);
        }
    }
}
