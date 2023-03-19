package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);
    @Query("select new peaksoft.dto.response.UserResponse(u.id,concat(u.firstName,' ',u.lastName),u.dateOfBirth,u.email,u.phoneNumber,u.role ) from User u")
    List<UserResponse>getAllResponse();
}