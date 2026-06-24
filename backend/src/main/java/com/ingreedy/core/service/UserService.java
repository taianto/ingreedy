package com.ingreedy.core.service;

import com.ingreedy.core.dto.user.UserRequest;
import com.ingreedy.core.dto.user.UserResponse;
import com.ingreedy.core.exception.DuplicateEmailException;
import com.ingreedy.core.exception.UserNotFoundException;
import com.ingreedy.core.model.User;
import com.ingreedy.core.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse create(UserRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();
        ensureEmailIsAvailable(normalizedEmail);

        User user = new User(normalizedEmail, request.displayName().trim());
        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> list() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        User user = getEntityById(id);
        return toResponse(user);
    }

    public UserResponse update(Long id, UserRequest request) {
        User user = getEntityById(id);
        String normalizedEmail = request.email().trim().toLowerCase();

        if (userRepository.existsByEmailAndIdNot(normalizedEmail, id)) {
            throw new DuplicateEmailException(normalizedEmail);
        }

        user.updateFrom(normalizedEmail, request.displayName().trim());
        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    public void delete(Long id) {
        User user = getEntityById(id);
        userRepository.delete(user);
    }

    private User getEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void ensureEmailIsAvailable(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getDisplayName(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
