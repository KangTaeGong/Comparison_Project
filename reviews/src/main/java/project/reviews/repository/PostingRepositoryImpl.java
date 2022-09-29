package project.reviews.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.reviews.domain.Posting;
import project.reviews.domain.QPosting;
import project.reviews.dto.PostingResponseDto;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static project.reviews.domain.QPosting.*;

@Repository
@RequiredArgsConstructor
public class PostingRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    QPosting qPosting = posting;

    public void create(Posting posting) {
        em.persist(posting);
    }

    /*
    * 커뮤니티 글 전체 리스트 가져오는 메소드
    * 내림차순으로 나열
    * */
    public List<PostingResponseDto> getList() {

        return queryFactory
                .select(Projections.constructor(PostingResponseDto.class,
                        posting.id,
                        posting.title,
                        posting.content,
                        posting.writer,
                        posting.hits))
                .from(posting)
                .orderBy(posting.id.desc())
                .fetch();
    }
    
    /*
    * 하나의 글 조회시 글의 정보를 가져오는 메소드
    * getList() 리턴값에서 id 값을 이욯해 필요한 포스팅 정보만 필터링
    * */
    public Optional<PostingResponseDto> getPosting(Long postingId) {
        return getList().stream()
                .filter(postingResponseDto -> postingResponseDto.getId().equals(postingId))
                .findFirst();
    }
}
