package toyproject.blogPosting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import toyproject.blogPosting.entity.Comment;
import toyproject.blogPosting.repository.resultSet.GetCommentListResultSet;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value =
            "select " +
            "u.nickname as nickname, " +
            "u.profile_image as profileImage, " +
            "c.write_datetime as writeDatetime, " +
            "c.content as content " +
            "from comment as c inner join user as u " +
            "on c.user_email=u.email " +
            "where c.board_number = ?1 " +
            "order by writeDatetime desc ", nativeQuery = true)
    List<GetCommentListResultSet> getCommentList(Integer boardNumber);

}
