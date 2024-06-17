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
public class GetSearchBoardListResponseDto extends ResponseDto {

    private List<BoardListItem> searchList;

    private GetSearchBoardListResponseDto(List<BoardListView> boardListViewList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.searchList = BoardListItem.getList(boardListViewList);
    }

    public static ResponseEntity<GetSearchBoardListResponseDto> success(List<BoardListView> boardListViewList) {
        GetSearchBoardListResponseDto result = new GetSearchBoardListResponseDto(boardListViewList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
