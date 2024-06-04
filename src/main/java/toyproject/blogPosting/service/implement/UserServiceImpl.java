package toyproject.blogPosting.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import toyproject.blogPosting.dto.response.ResponseDto;
import toyproject.blogPosting.dto.response.user.GetSignInUserResponseDto;
import toyproject.blogPosting.entity.User;
import toyproject.blogPosting.repository.UserRepository;
import toyproject.blogPosting.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {

        User user = null;

        try {
            user = userRepository.findByEmail(email);
            if (user == null) {
                return GetSignInUserResponseDto.notExistUser();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetSignInUserResponseDto.success(user);
    }
}
