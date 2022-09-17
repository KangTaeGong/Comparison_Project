package project.reviews.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserRepository {

    private final EntityManager em;

    // 회원을 저장하는 메소드
    public void save(User user_info) {
        em.persist(user_info);
    }

    /*
    * DB ID값으로 회원 조회
    * 테스트 코드에서 사용
    * */
    public Optional<User> findById(Long id) {
        User findUser = em.find(User.class, id);
        return Optional.ofNullable(findUser);
    }

    /*
    * 회원 아이디로 회원 검색(회원가입시 중복 확인으로 사용)
    * getSingleResult()는 결과가 없으면 예외가 발생하기 때문에 사용 X
    * getResultList()는 결과가 없으면 빈 컬렉션을 반환하기 때문에 사용
    * */
    public List<User> findByUserId(String userId) {
        return em.createQuery("select u from User u where u.userId = :userId", User.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}