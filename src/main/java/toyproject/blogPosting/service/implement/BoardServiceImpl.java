package toyproject.blogPosting.service.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import toyproject.blogPosting.dto.request.board.PatchBoardRequestDto;
import toyproject.blogPosting.dto.request.board.PostCommentRequestDto;
import toyproject.blogPosting.dto.response.board.*;
import toyproject.blogPosting.dto.request.board.PostBoardRequestDto;
import toyproject.blogPosting.dto.response.ResponseDto;
import toyproject.blogPosting.entity.*;
import toyproject.blogPosting.repository.*;
import toyproject.blogPosting.repository.resultSet.GetBoardResultSet;
import toyproject.blogPosting.repository.resultSet.GetCommentListResultSet;
import toyproject.blogPosting.repository.resultSet.GetFavoriteListResultSet;
import toyproject.blogPosting.service.BoardService;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final FavoriteRepository favoriteRepository;
    private final CommentRepository commentRepository;
    private final BoardListViewRepository boardListViewRepository;
    private final SearchLogRepository searchLogRepository;

    @Override
    public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email) {

        try {

            boolean existedEmail = userRepository.existsByEmail(email);
            if (!existedEmail) {
                return PostBoardResponseDto.notExistUser();
            }

            Board board = new Board(dto, email);
            boardRepository.save(board);

            int boardNumber = board.getBoardNumber();
            List<String> boardImageList = dto.getBoardImageList();
            List<Image> images = new ArrayList<>();

            for (String image : boardImageList) {
                Image img = new Image(boardNumber, image);
                images.add(img);
            }

            imageRepository.saveAll(images);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostBoardResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {

        GetBoardResultSet resultSet = null;
        List<Image> images = new ArrayList<>();

        try {

            resultSet = boardRepository.getBoard(boardNumber);
            if (resultSet == null) {
                return GetBoardResponseDto.notExistBoard();
            }

            images = imageRepository.findByBoardNumber(boardNumber);

//            Board board = boardRepository.findByBoardNumber(boardNumber);
//            board.increaseViewCount();
//            boardRepository.save(board);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetBoardResponseDto.success(resultSet, images);
    }

    @Override
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email) {

        try {

            boolean existedUser = userRepository.existsByEmail(email);
            if(!existedUser) return PutFavoriteResponseDto.noExistUser();

            Board board = boardRepository.findByBoardNumber(boardNumber);
            if (board == null) {
                return PutFavoriteResponseDto.noExistBoard();
            }

            Favorite favorite = favoriteRepository.findByBoardNumberAndUserEmail(boardNumber, email);
            if (favorite == null) {
                favorite = new Favorite(email, boardNumber);
                favoriteRepository.save(favorite);
                board.increaseFavoriteCount();
            } else {
                favoriteRepository.delete(favorite);
                board.decreaseFavoriteCount();
            }

            boardRepository.save(board);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PutFavoriteResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber) {

        List<GetFavoriteListResultSet> resultSets = new ArrayList<>();

        try {

            boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
            if(!existedBoard) return GetFavoriteListResponseDto.noExistBoard();

            resultSets = favoriteRepository.getFavoriteList(boardNumber);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetFavoriteListResponseDto.success(resultSets);
    }

    @Override
    public ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email) {

        try {

            Board board = boardRepository.findByBoardNumber(boardNumber);
            if (board == null) {
                return PostCommentResponseDto.noExistBoard();
            }

            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser) {
                return PostCommentResponseDto.noExistBoard();
            }

            Comment comment = new Comment(dto, boardNumber, email);
            commentRepository.save(comment);

            board.increaseCommentCount();
            boardRepository.save(board);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostCommentResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber) {

        List<GetCommentListResultSet> resultSets = new ArrayList<>();

        try {

            boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
            if (!existedBoard) {
                return GetCommentListResponseDto.noExistBoard();
            }

            resultSets = commentRepository.getCommentList(boardNumber);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetCommentListResponseDto.success(resultSets);
    }

    @Override
    public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber) {

        try {

            Board board = boardRepository.findByBoardNumber(boardNumber);
            if(board == null) return IncreaseViewCountResponseDto.notExistBoard();

            board.increaseViewCount();
            boardRepository.save(board);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return IncreaseViewCountResponseDto.success();
    }

    @Override
    public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, String email) {

        try {

            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser) {
                return DeleteBoardResponseDto.noExistUser();
            }

            Board board = boardRepository.findByBoardNumber(boardNumber);
            if (board == null) {
                return DeleteBoardResponseDto.noExistBoard();
            }

            String writerEmail = board.getWriterEmail();
            boolean isWriter = writerEmail.equals(email);

            if (!isWriter) {
                return DeleteBoardResponseDto.noPermission();
            }

            imageRepository.deleteByBoardNumber(boardNumber);
            commentRepository.deleteByBoardNumber(boardNumber);
            favoriteRepository.deleteByBoardNumber(boardNumber);
            boardRepository.delete(board);


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return DeleteBoardResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, String email) {

        try {

            Board board = boardRepository.findByBoardNumber(boardNumber);
            if (board == null) {
                return PatchBoardResponseDto.notExistBoard();
            }

            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser) {
                return PatchBoardResponseDto.notExistUser();
            }

            String writerEmail = board.getWriterEmail();
            boolean isWriter = writerEmail.equals(email);
            if (!isWriter) {
                return PatchBoardResponseDto.noPermission();
            }

            board.patchBoard(dto);
            boardRepository.save(board);

            imageRepository.deleteByBoardNumber(boardNumber); // 원래 존재했던 이미지를 모두 지운다
            List<String> boardImageList = dto.getBoardImageList();
            List<Image> images = new ArrayList<>();

            for (String boardImage : boardImageList) {
                Image image = new Image(boardNumber, boardImage);
                images.add(image);
            }

            imageRepository.saveAll(images);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PatchBoardResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList() {

        List<BoardListView> boardListViewList = new ArrayList<>();

        try {

            boardListViewList = boardListViewRepository.findByOrderByWriteDatetimeDesc();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetLatestBoardListResponseDto.success(boardListViewList);
    }

    @Override
    public ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList() {

        List<BoardListView> boardListViewList = new ArrayList<>();

        try {

            Date beforeWeek = Date.from(Instant.now().minus(7, ChronoUnit.DAYS));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sevenDaysAgo = simpleDateFormat.format(beforeWeek);

            boardListViewList = boardListViewRepository.findTop3ByWriteDatetimeGreaterThanOrderByFavoriteCountDescCommentCountDescViewCountDescWriteDatetimeDesc(sevenDaysAgo);

        } catch (Exception e) {

            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetTop3BoardListResponseDto.success(boardListViewList);
    }

    @Override
    public ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord, String preSearchWord) {

        List<BoardListView> boardListViewList = new ArrayList<>();

        try {

            boardListViewList = boardListViewRepository.findByTitleContainsOrContentContainsOrderByWriteDatetimeDesc(searchWord, searchWord);

            SearchLog searchLog = new SearchLog(searchWord, preSearchWord, false);
            searchLogRepository.save(searchLog);

            boolean relation = preSearchWord != null;
            if (relation) {
                SearchLog searchLog2 = new SearchLog(preSearchWord, searchWord, relation);
                searchLogRepository.save(searchLog2);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetSearchBoardListResponseDto.success(boardListViewList);
    }
}
