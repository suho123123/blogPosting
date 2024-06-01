package toyproject.blogPosting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.blogPosting.entity.SearchLog;

public interface SearchLogRepository extends JpaRepository<SearchLog, Integer> {


}
