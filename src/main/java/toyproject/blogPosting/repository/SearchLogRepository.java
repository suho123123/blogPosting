package toyproject.blogPosting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import toyproject.blogPosting.entity.SearchLog;
import toyproject.blogPosting.repository.resultSet.GetPopularListResultSet;

import java.util.List;

@Repository
public interface SearchLogRepository extends JpaRepository<SearchLog, Integer> {

    @Query(value =
            "select search_word as searchWord, count(search_word) as count " +
                    "from search_log " +
                    "where relation is false " +
                    "group by search_word " +
                    "order by count desc " +
                    "limit 15", nativeQuery = true)
    List<GetPopularListResultSet> getPopularList();

}
