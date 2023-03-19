package peaksoft.services;

import peaksoft.dto.request.AuthenticateRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.User;

import java.util.List;

/**
 * name : kutman
 **/
public interface UserServices {
    UserResponse saveUser(UserRequest userRequest);
    UserResponse getUserById(Long id);
    List<UserResponse>getAllUser();
    String deleteUser(Long id);
    UserResponse updateUser(Long id,UserRequest userRequest);
    AuthenticationResponse  login(AuthenticateRequest request);
}
