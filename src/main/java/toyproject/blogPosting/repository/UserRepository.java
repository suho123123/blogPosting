package toyproject.blogPosting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyproject.blogPosting.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {


}
