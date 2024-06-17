package toyproject.blogPosting.service;

import org.springframework.http.ResponseEntity;
import toyproject.blogPosting.dto.response.search.GetPopularListResponseDto;
import toyproject.blogPosting.dto.response.search.GetRelationListResponseDto;

public interface SearchService {

    ResponseEntity<? super GetPopularListResponseDto> getPopularList();

    ResponseEntity<? super GetRelationListResponseDto> getRelationList(String searchWord);
}
