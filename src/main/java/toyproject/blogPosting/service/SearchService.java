package toyproject.blogPosting.service;

import org.springframework.http.ResponseEntity;
import toyproject.blogPosting.dto.response.search.GetPopularListResponseDto;

public interface SearchService {

    ResponseEntity<? super GetPopularListResponseDto> getPopularList();
}
