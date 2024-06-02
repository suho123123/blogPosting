package toyproject.blogPosting.service;

import org.springframework.http.ResponseEntity;
import toyproject.blogPosting.dto.request.auth.SignInRequestDto;
import toyproject.blogPosting.dto.request.auth.SignUpRequestDto;
import toyproject.blogPosting.dto.response.auth.SignInResponseDto;
import toyproject.blogPosting.dto.response.auth.SignUpResponseDto;

public interface AuthService {

    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);

    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
