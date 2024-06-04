package toyproject.blogPosting.service;

import org.springframework.http.ResponseEntity;
import toyproject.blogPosting.dto.response.user.GetSignInUserResponseDto;

public interface UserService {

    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
}
