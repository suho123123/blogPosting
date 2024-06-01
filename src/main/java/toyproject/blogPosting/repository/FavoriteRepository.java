package toyproject.blogPosting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.blogPosting.entity.Favorite;
import toyproject.blogPosting.entity.primaryKey.FavoritePk;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoritePk> {
}
