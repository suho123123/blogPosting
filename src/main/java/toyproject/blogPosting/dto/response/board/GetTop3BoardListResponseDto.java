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
public class GetTop3BoardListResponseDto extends ResponseDto {

    private List<BoardListItem> top3List;

    private GetTop3BoardListResponseDto(List<BoardListView> boardListViewList) {

        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.top3List = BoardListItem.getList(boardListViewList);
    }

    public static ResponseEntity<GetTop3BoardListResponseDto> success(List<BoardListView> boardListViewList) {

        GetTop3BoardListResponseDto result = new GetTop3BoardListResponseDto(boardListViewList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
