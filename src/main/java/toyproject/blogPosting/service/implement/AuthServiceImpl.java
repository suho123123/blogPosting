package toyproject.blogPosting.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toyproject.blogPosting.dto.request.auth.SignInRequestDto;
import toyproject.blogPosting.dto.request.auth.SignUpRequestDto;
import toyproject.blogPosting.dto.response.ResponseDto;
import toyproject.blogPosting.dto.response.auth.SignInResponseDto;
import toyproject.blogPosting.dto.response.auth.SignUpResponseDto;
import toyproject.blogPosting.entity.User;
import toyproject.blogPosting.provider.JwtProvider;
import toyproject.blogPosting.repository.UserRepository;
import toyproject.blogPosting.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {

        try {

            String email = dto.getEmail();
            boolean existedEmail = userRepository.existsByEmail(email);
            if (existedEmail) {
                return SignUpResponseDto.duplicateEmail();
            }

            String nickname = dto.getNickname();
            boolean existedNickname = userRepository.existsByNickname(nickname);
            if (existedNickname) {
                return SignUpResponseDto.duplicateNickname();
            }

            String telNumber = dto.getTelNumber();
            boolean existedTelNumber = userRepository.existsByTelNumber(telNumber);
            if (existedTelNumber) {
                return SignUpResponseDto.duplicateTelNumber();
            }

            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            User user = new User(dto);
            userRepository.save(user);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {

        String token = null;

        try {

            String email = dto.getEmail();
            User user = userRepository.findByEmail(email);

            if (user == null) {
                return SignInResponseDto.signInFailed();
            }

            String password = dto.getPassword();
            String encodedPassword = user.getPassword();

            boolean isMatched = passwordEncoder.matches(password, encodedPassword);

            if (!isMatched) {
                return SignInResponseDto.signInFailed();
            }

            // token 생성
            token = jwtProvider.create(email);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(token);
    }
}
