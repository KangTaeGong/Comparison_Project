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
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager em;

    @Override
    public void save(User user_info) {
        em.persist(user_info);
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }


    /*
    * 회원 아이디로 회원 엔티티 조회
    * Session을 통해 유저 정보를 가져올 때 id값이 없기 때문에 새로 조회하기 위해 사용
    * */
    @Override
    public User loadUserByUserId(String userId) {
        return (User) em.createQuery("select u from User u where u.userId =: userId")
                .setParameter("userId", userId)
                .getSingleResult();
    }


    // 회원 아이디로 회원 검색(회원가입 / 로그인시 확인용으로 사용)
    @Override
    public Optional<FindUserDto> findByUserId(String userId) {
        return findAll().stream()
                .filter(findUserDto -> findUserDto.getUserId().equals(userId))
                .findFirst();
    }


    // 전체 회원 조회
    @Override
    public List<FindUserDto> findAll() {
        return em.createQuery(
                "select new project.reviews.dto.FindUserDto(u.userId, u.userName, u.password)" +
                " from User u", FindUserDto.class)
                .getResultList();
    }

    @Override
    public void deleteUser(User user) {
        em.remove(user);
    }
}