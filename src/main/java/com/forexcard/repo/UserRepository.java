package com.forexcard.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.forexcard.dto.PendingUserDTO;
import com.forexcard.dto.UserDTO;
import com.forexcard.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByEmail(String email);  // Correct way to return Optional for null safety
    boolean existsByEmail(String email);
    
    void deleteAll();  // Be cautious when using this in production
    
    @Query("SELECT u FROM User u JOIN FETCH u.forexCard WHERE u.name = :name")
    Optional<User> findByNameWithCard(String name);  // Fetch the user with their forex card
    
    @Query("SELECT new com.forexcard.dto.PendingUserDTO(u.id, u.name, u.dob, u.gender, u.address, u.state, u.country, u.email, u.phonenumber, u.salary, u.pan, u.adminAction) FROM User u WHERE u.adminAction = :adminAction")
    List<PendingUserDTO> findPendingUsers(@Param("adminAction") String adminAction);

    
    @Query("SELECT new com.forexcard.dto.UserDTO(u.name, u.adminAction, fc.status, fc.cardNumber) " +
            "FROM User u LEFT JOIN u.forexCard fc WHERE u.id = :id")
     UserDTO findUserDTOById(@Param("id") Integer id);
        

    @Query("SELECT new com.forexcard.dto.PendingUserDTO(u.id, u.name, u.dob, u.gender, u.address, u.state, u.country, u.email, u.phonenumber, u.salary, u.pan, u.adminAction) FROM User u WHERE u.adminAction = :action")
    List<PendingUserDTO> findPendingUserDTOsByAdminAction(@Param("action") String action);
    
    @Query("SELECT new com.forexcard.dto.PendingUserDTO(u.id, u.name, u.dob, u.gender, u.address, u.state, u.country, u.email, u.phonenumber, u.salary, u.pan, u.adminAction) FROM User u WHERE u.id = :id")
	PendingUserDTO findUserProfileByid(@Param("id") Integer id);
}
