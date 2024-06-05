package toyproject.blogPosting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.blogPosting.entity.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {


    List<Image> findByBoardNumber(Integer boardNumber);
}
