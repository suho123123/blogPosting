package toyproject.blogPosting.dto.response.search;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import toyproject.blogPosting.common.ResponseCode;
import toyproject.blogPosting.common.ResponseMessage;
import toyproject.blogPosting.dto.response.ResponseDto;
import toyproject.blogPosting.repository.resultSet.GetPopularListResultSet;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetPopularListResponseDto extends ResponseDto {

    private List<String> popularWordList;

    private GetPopularListResponseDto(List<GetPopularListResultSet> resultSetList) {

        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        List<String> popularWordList = new ArrayList<>();
        for (GetPopularListResultSet resultSet : resultSetList) {
            String popularWord = resultSet.getSearchWord();
            popularWordList.add(popularWord);
        }
        this.popularWordList = popularWordList;

    }

    public static ResponseEntity<GetPopularListResponseDto> success(List<GetPopularListResultSet> resultSetList) {
        GetPopularListResponseDto result = new GetPopularListResponseDto(resultSetList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
