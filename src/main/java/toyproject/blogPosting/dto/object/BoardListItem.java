package toyproject.blogPosting.dto.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.blogPosting.entity.BoardListView;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardListItem {

    private int boardNumber;
    private String title;
    private String content;
    private String boardTitleImage;
    private int favoriteCount;
    private int commentCount;
    private int viewCount;
    private String writeDatetime;
    private String writerNickname;
    private String writerProfileImage;

    public BoardListItem(BoardListView boardListView) {

        this.boardNumber = boardListView.getBoardNumber();
        this.title = boardListView.getTitle();
        this.content = boardListView.getContent();
        this.boardTitleImage = boardListView.getTitleImage();
        this.favoriteCount = boardListView.getFavoriteCount();
        this.commentCount = boardListView.getCommentCount();
        this.viewCount = boardListView.getViewCount();
        this.writeDatetime = boardListView.getWriteDatetime();
        this.writerNickname = boardListView.getWriterNickname();
        this.writerProfileImage = boardListView.getWriterProfileImage();
    }

    public static List<BoardListItem> getList(List<BoardListView> boardListViewList) {
        List<BoardListItem> list = new ArrayList<>();
        for (BoardListView boardListView : boardListViewList) {
            BoardListItem boardListItem = new BoardListItem(boardListView);
            list.add(boardListItem);
        }

        return list;
    }
}
