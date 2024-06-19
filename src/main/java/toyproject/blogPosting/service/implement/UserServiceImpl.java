package toyproject.blogPosting.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import toyproject.blogPosting.dto.request.user.PatchNicknameRequestDto;
import toyproject.blogPosting.dto.request.user.PatchProfileImageRequestDto;
import toyproject.blogPosting.dto.response.ResponseDto;
import toyproject.blogPosting.dto.response.user.GetSignInUserResponseDto;
import toyproject.blogPosting.dto.response.user.GetUserResponseDto;
import toyproject.blogPosting.dto.response.user.PatchNicknameResponseDto;
import toyproject.blogPosting.dto.response.user.PatchProfileImageResponseDto;
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

    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(String email) {

        User user = null;

        try {

            user = userRepository.findByEmail(email);
            if (user == null) {
                return GetUserResponseDto.noExistUser();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetUserResponseDto.success(user);
    }

    @Override
    public ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto, String email) {

        try {

            User user = userRepository.findByEmail(email);
            if (user == null) {
                PatchNicknameResponseDto.noExistUser();
            }

            String nickname = dto.getNickname();
            boolean existsByNickname = userRepository.existsByNickname(nickname);

            if (existsByNickname) {
                return PatchNicknameResponseDto.duplicateNickname();
            }

            user.setNickname(nickname);
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PatchNicknameResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto dto, String email) {

        try {

            User user = userRepository.findByEmail(email);
            if (user == null) {
                return PatchProfileImageResponseDto.noExistUser();
            }

            String profileImage = dto.getProfileImage();
            user.setProfileImage(profileImage);
            userRepository.save(user);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PatchProfileImageResponseDto.success();
    }
}
