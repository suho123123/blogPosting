package toyproject.blogPosting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.blogPosting.entity.BoardListView;

public interface BoardListViewRepository extends JpaRepository<BoardListView, Integer> {


}
