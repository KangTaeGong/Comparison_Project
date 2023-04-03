package project.reviews.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.domain.User;
import project.reviews.dto.FindUserDto;

import java.util.List;

/*
* 2023-02-25
* UserRepositoryImpl Test Code
* */
@SpringBootTest
@Transactional
public class UserRepositoryImplTest {

    @Autowired UserRepository userRepository;

    @Test
    void delete_user() {
        //given
        User user = new User("김길이", "kingill4223", "abcd1234@");
        userRepository.save(user);

        //when
        userRepository.deleteUser(user);

        List<FindUserDto> all_user = userRepository.findAll();

        //then
        Assertions.assertThat(all_user)
                .extracting("userName")
                .contains("홍길동");

        org.junit.jupiter.api.Assertions.assertEquals(1, all_user.size());
    }
}
