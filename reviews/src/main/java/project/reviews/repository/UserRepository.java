package project.reviews.repository;

import project.reviews.domain.User;
import project.reviews.dto.FindUserDto;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user_info);

    User findById(Long id);

    User loadUserByUserId(String userId);

    Optional<FindUserDto> findByUserId(String userId);

    List<FindUserDto> findAll();
}
