package toyproject.blogPosting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.blogPosting.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {


}
