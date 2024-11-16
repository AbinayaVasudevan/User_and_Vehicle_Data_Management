package com.te.Repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.Entity.User;
@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	Optional<User> findByMobileNumber(String mobileNumber);


	void deleteByMobileNumber(User user);


	boolean existsByMobileNumberAndIdNot(String mobileNumber, Long id);


}
