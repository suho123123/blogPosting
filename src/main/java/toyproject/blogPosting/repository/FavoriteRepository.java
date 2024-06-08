package toyproject.blogPosting.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import toyproject.blogPosting.entity.Favorite;
import toyproject.blogPosting.entity.primaryKey.FavoritePk;
import toyproject.blogPosting.repository.resultSet.GetFavoriteListResultSet;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoritePk> {

    Favorite findByBoardNumberAndUserEmail(Integer boardNumber, String userEmail);

    @Query(value =
            "select " +
                    "u.email as email, " +
                    "u.nickname as nickname, " +
                    "u.profile_image " +
                    "from favorite as f inner join user as u " +
                    "on f.user_email = u.email " +
                    "where f.board_number = ?1", nativeQuery = true)
    List<GetFavoriteListResultSet> getFavoriteList(Integer boardNumber);

    @Transactional
    void deleteByBoardNumber(Integer boardNumber);

}
