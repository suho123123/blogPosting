package toyproject.blogPosting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.blogPosting.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {


}
