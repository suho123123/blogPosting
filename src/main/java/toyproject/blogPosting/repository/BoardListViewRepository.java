package toyproject.blogPosting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyproject.blogPosting.entity.BoardListView;

import java.util.List;

@Repository
public interface BoardListViewRepository extends JpaRepository<BoardListView, Integer> {

    List<BoardListView> findByOrderByWriteDatetimeDesc();

    List<BoardListView> findTop3ByWriteDatetimeGreaterThanOrderByFavoriteCountDescCommentCountDescViewCountDescWriteDatetimeDesc(String WriteDatetime);

    List<BoardListView> findByTitleContainsOrContentContainsOrderByWriteDatetimeDesc(String title, String content);

    List<BoardListView> findByWriterEmailOrderByWriteDatetimeDesc(String writerEmail);
}
