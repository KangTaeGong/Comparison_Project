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
public class UserRepository {

    private final EntityManager em;

    // 회원을 저장하는 메소드
    public User save(User user_info) {
        em.persist(user_info);
        return user_info;
    }

    // 회원 정보 검색(패스워드 제외)
    public Optional<FindUserDto> findById(Long id) {
        FindUserDto findUser = em.find(FindUserDto.class, id);
        return Optional.ofNullable(findUser);
    }

    /*
    * 회원 이름으로 회원 검색(회원가입시 중복 확인으로 사용)
    * getSingleResult()는 결과가 없으면 예외가 발생하기 때문에 사용 X
    * getResultList()는 결과가 없으면 빈 컬렉션을 반환하기 때문에 사용
    * */
    public List<FindUserDto> findByName(String userName) {
        return em.createQuery("select u from User u where u.username = :username", FindUserDto.class)
                .setParameter("username", userName)
                .getResultList();
    }
}
