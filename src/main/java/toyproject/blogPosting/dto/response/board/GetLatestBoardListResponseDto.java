package toyproject.blogPosting.dto.response.board;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import toyproject.blogPosting.common.ResponseCode;
import toyproject.blogPosting.common.ResponseMessage;
import toyproject.blogPosting.dto.object.BoardListItem;
import toyproject.blogPosting.dto.response.ResponseDto;
import toyproject.blogPosting.entity.BoardListView;

import java.util.List;

@Getter
public class GetLatestBoardListResponseDto extends ResponseDto {

    private List<BoardListItem> latestList;

    private GetLatestBoardListResponseDto(List<BoardListView> boardList) {

        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.latestList = BoardListItem.getList(boardList);
    }

    public static ResponseEntity<GetLatestBoardListResponseDto> success(List<BoardListView> boardList) {
        GetLatestBoardListResponseDto result = new GetLatestBoardListResponseDto(boardList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
