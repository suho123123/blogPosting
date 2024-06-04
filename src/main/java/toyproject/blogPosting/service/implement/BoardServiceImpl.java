package toyproject.blogPosting.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import toyproject.blogPosting.dto.request.board.PostBoardRequestDto;
import toyproject.blogPosting.dto.response.ResponseDto;
import toyproject.blogPosting.dto.response.board.PostBoardResponseDto;
import toyproject.blogPosting.entity.Board;
import toyproject.blogPosting.entity.Image;
import toyproject.blogPosting.repository.BoardRepository;
import toyproject.blogPosting.repository.ImageRepository;
import toyproject.blogPosting.repository.UserRepository;
import toyproject.blogPosting.service.BoardService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;

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
}
