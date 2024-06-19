package toyproject.blogPosting.service;

import org.springframework.http.ResponseEntity;
import toyproject.blogPosting.dto.request.user.PatchNicknameRequestDto;
import toyproject.blogPosting.dto.request.user.PatchProfileImageRequestDto;
import toyproject.blogPosting.dto.response.user.GetSignInUserResponseDto;
import toyproject.blogPosting.dto.response.user.GetUserResponseDto;
import toyproject.blogPosting.dto.response.user.PatchNicknameResponseDto;
import toyproject.blogPosting.dto.response.user.PatchProfileImageResponseDto;

public interface UserService {

    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);

    ResponseEntity<? super GetUserResponseDto> getUser(String email);

    ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto, String email);

    ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto dto, String email);
}
