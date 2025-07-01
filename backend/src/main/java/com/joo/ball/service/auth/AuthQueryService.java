package com.joo.ball.service.auth;

import com.joo.ball.dto.auth.request.SignInRequest;
import com.joo.ball.entity.User;
import com.joo.ball.repository.UserRepository;
import com.joo.ball.util.exception.auth.InvalidPasswordException;
import com.joo.ball.util.exception.auth.InvalidUsernameException;
import com.joo.ball.util.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthQueryService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String authenticateUser(SignInRequest signInRequest) {
        /*
         * 회원 인증
         * 1. DTO에 입력된 password 암호화하여 DB에 저장된 암호와 비교
         * 2. 성공 시 토큰 생성 및 반환
         * 3. 실패 시 실패 응답 전달
         */
        User user = userRepository.findByUsername(signInRequest.getUsername())
            .orElseThrow(() -> new InvalidUsernameException("존재하지 않는 계정입니다."));

        String encodedPassword = user.getPassword();

        if (!passwordEncoder.matches(signInRequest.getPassword(), encodedPassword)) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.generateToken(user);
    }
}
