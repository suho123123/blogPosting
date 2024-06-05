package toyproject.blogPosting.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import toyproject.blogPosting.dto.response.board.GetBoardResponseDto;
import toyproject.blogPosting.dto.request.board.PostBoardRequestDto;
import toyproject.blogPosting.dto.response.ResponseDto;
import toyproject.blogPosting.dto.response.board.GetFavoriteListResponseDto;
import toyproject.blogPosting.dto.response.board.PostBoardResponseDto;
import toyproject.blogPosting.dto.response.board.PutFavoriteResponseDto;
import toyproject.blogPosting.entity.Board;
import toyproject.blogPosting.entity.Favorite;
import toyproject.blogPosting.entity.Image;
import toyproject.blogPosting.repository.BoardRepository;
import toyproject.blogPosting.repository.FavoriteRepository;
import toyproject.blogPosting.repository.ImageRepository;
import toyproject.blogPosting.repository.UserRepository;
import toyproject.blogPosting.repository.resultSet.GetBoardResultSet;
import toyproject.blogPosting.repository.resultSet.GetFavoriteListResultSet;
import toyproject.blogPosting.service.BoardService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final FavoriteRepository favoriteRepository;

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

            Board board = boardRepository.findByBoardNumber(boardNumber);
            board.increaseViewCount();
            boardRepository.save(board);

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
}
