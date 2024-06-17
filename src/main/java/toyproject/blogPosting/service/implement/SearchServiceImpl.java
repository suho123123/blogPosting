package toyproject.blogPosting.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import toyproject.blogPosting.dto.response.ResponseDto;
import toyproject.blogPosting.dto.response.search.GetPopularListResponseDto;
import toyproject.blogPosting.dto.response.search.GetRelationListResponseDto;
import toyproject.blogPosting.repository.SearchLogRepository;
import toyproject.blogPosting.repository.resultSet.GetPopularListResultSet;
import toyproject.blogPosting.repository.resultSet.GetRelationListResultSet;
import toyproject.blogPosting.service.SearchService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchLogRepository searchLogRepository;

    @Override
    public ResponseEntity<? super GetPopularListResponseDto> getPopularList() {

        List<GetPopularListResultSet> resultSetList = new ArrayList<>();

        try {

            resultSetList = searchLogRepository.getPopularList();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetPopularListResponseDto.success(resultSetList);
    }

    @Override
    public ResponseEntity<? super GetRelationListResponseDto> getRelationList(String searchWord) {

        List<GetRelationListResultSet> resultSets = new ArrayList<>();

        try {

            resultSets = searchLogRepository.getRelationList(searchWord);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetRelationListResponseDto.success(resultSets);
    }
}
