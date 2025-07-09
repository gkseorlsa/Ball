package com.joo.ball.service.auth;

import com.joo.ball.dto.auth.request.SignUpRequest;
import com.joo.ball.entity.user.User;
import com.joo.ball.factory.UserFactory;
import com.joo.ball.repository.user.UserRepository;
import com.joo.ball.util.exception.DuplicateUsernameException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthCommandService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
    public void createUser(SignUpRequest signUpRequest) {
		userRepository.findByUsername(signUpRequest.username())
			.ifPresent(user -> {
				throw new DuplicateUsernameException("이미 존재하는 계정입니다.");
			});

		String encryptedPassword = passwordEncoder.encode(signUpRequest.password());

		User newUser = UserFactory.of(signUpRequest, encryptedPassword);
        userRepository.save(newUser);
    }
}
