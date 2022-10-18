package project.reviews.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.reviews.domain.User;
import project.reviews.dto.FindUserDto;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/*
* 2022-09-16 생성
* 회원 관련 Repository
* */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager em;

    // 회원을 저장하는 메소드
    @Override
    public void save(User user_info) {
        em.persist(user_info);
    }

    /*
    * DB ID값으로 회원 조회
    * 테스트 코드에서 사용
    * */
    @Override
    public User findById(Long id) {
        User findUser = em.find(User.class, id);
        return findUser != null ? findUser : null;
    }

    /*
    * 회원 아이디로 회원 검색
    * Security UserDetailService 에서 사용
    * */
    @Override
    public User loadUserByUserId(String userId) {
        User findUser = (User) em.createQuery("select u from User u where u.userId =: userId")
                .setParameter("userId", userId)
                .getSingleResult();

        return findUser != null ? findUser : null;
    }

    /*
    * 회원 아이디로 회원 검색(회원가입 / 로그인시 확인용으로 사용)
    * */
    @Override
    public Optional<FindUserDto> findByUserId(String userId) {
        return findAll().stream()
                .filter(findUserDto -> findUserDto.getUserId().equals(userId))
                .findFirst();
    }

    /*
    * 전체 회원 조회
    * */
    @Override
    public List<FindUserDto> findAll() {
        return em.createQuery(
                "select new project.reviews.dto.FindUserDto(u.userId, u.userName, u.password)" +
                " from User u", FindUserDto.class)
                .getResultList();
    }
}