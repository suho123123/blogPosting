package toyproject.blogPosting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyproject.blogPosting.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

}
