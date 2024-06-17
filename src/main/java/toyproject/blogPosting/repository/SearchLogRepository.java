package toyproject.blogPosting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import toyproject.blogPosting.dto.response.search.GetRelationListResponseDto;
import toyproject.blogPosting.entity.SearchLog;
import toyproject.blogPosting.repository.resultSet.GetPopularListResultSet;
import toyproject.blogPosting.repository.resultSet.GetRelationListResultSet;

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

    @Query(
            value = "select relation_word as searchWord, count(relation_word) as count " +
                    "from search_log " +
                    "where search_word = ?1 " +
                    "and relation_word is not null " +
                    "group by relation_word " +
                    "order by count desc " +
                    "limit 15", nativeQuery = true
    )
    List<GetRelationListResultSet> getRelationList(String searchWord);

}
