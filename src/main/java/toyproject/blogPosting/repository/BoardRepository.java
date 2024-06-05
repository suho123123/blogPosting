package toyproject.blogPosting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import toyproject.blogPosting.entity.Board;
import toyproject.blogPosting.repository.resultSet.GetBoardResultSet;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query(value =
            "select " +
            "b.board_number as boardNumber, " +
            "b.title as title, " +
            "b.content as content, " +
            "b.write_datetime as writeDatetime, " +
            "b.writer_email as writerEmail, " +
            "u.nickname as writerNickname, " +
            "u.profile_image as writerProfileImage " +
            "from board as b inner join user as u on b.writer_email = u.email " +
            "where board_number = ?1 ", nativeQuery = true)
    GetBoardResultSet getBoard(Integer boardNumber);

    Board findByBoardNumber(Integer boardNumber);

    boolean existsByBoardNumber(Integer boardNumber);
}
