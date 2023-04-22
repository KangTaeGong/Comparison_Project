package project.reviews.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import project.reviews.domain.Posting;
import project.reviews.domain.QPosting;
import project.reviews.dto.PostingResponseDto;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static project.reviews.domain.QPosting.*;

/*
* 2022-09-30
* 게시판 관련 Repository
* */
@Repository
public class PostingRepositoryImpl implements PostingRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PostingRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    QPosting qPosting = posting;

    @Override
    public Long create(Posting posting) {
        em.persist(posting);
        return posting.getId();
    }

    // 커뮤니티 전체 글 페이징 추가해서 받아오기
    @Override
    public Page<PostingResponseDto> getListPaging(Pageable pageable, String postingSearch) {
        List<PostingResponseDto> pagingList = queryFactory
                .select(Projections.constructor(PostingResponseDto.class,
                        posting.id,
                        posting.title,
                        posting.content,
                        posting.writer,
                        posting.password,
                        posting.hits,
                        posting.createdDate))
                .from(posting)
                .where(
                        postingInfoLike(postingSearch))
                .orderBy(posting.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(posting.count())
                .from(posting);

        return PageableExecutionUtils.getPage(pagingList, pageable, countQuery::fetchOne);
    }

    /*
    * 게시글 검색시 넘겨받은 값을 통해 일치하는 값이 있는지 확인
    * 해당 text를 포함하는 값이 있다면 where문에 넣어 반환
    * 해당 값이 없다면 null 반환
    * null을 반환하게 되면 where문은 자동으로 동작하지 않는다.
    * */
    private BooleanExpression postingInfoLike(String postingInfo) {
        return StringUtils.hasText(postingInfo) ? posting.title.contains(postingInfo)
                .or(posting.writer.contains(postingInfo)) : null;
    }

    // 게시글 개수 카운팅
    public Long getPostingCount() {
        return queryFactory
                .select(posting.count())
                .from(posting)
                .fetchOne();
    }


    // 커뮤니티 글 전체 리스트 가져오는 메소드
    public List<PostingResponseDto> getList() {
        return queryFactory
                .select(Projections.constructor(PostingResponseDto.class,
                        posting.id,
                        posting.title,
                        posting.content,
                        posting.writer,
                        posting.password,
                        posting.hits))
                .from(posting)
                .fetch();
    }
    
    /*
    * 하나의 글 조회시 글의 정보를 가져오는 메소드
    * getList() 리턴값에서 id 값을 이용해 필요한 포스팅 정보만 필터링
    * */
    public Optional<PostingResponseDto> getPosting(Long postingId) {
        return getList().stream()
                .filter(postingResponseDto -> postingResponseDto.getId().equals(postingId))
                .findFirst();
    }

    /*
    * Id로 조회하여 Entity 반환, 값이 없으면 null 반환
    * 게시글 수정시에 사용
    * Dirty Checking 용도
    * */
    public Posting findPostingById(Long postingId) {
        return em.find(Posting.class, postingId);
    }

    // 게시글 삭제
    @Override
    public void delete_Posting(Long postingId) {
        queryFactory
                .delete(posting)
                .where(posting.id.eq(postingId))
                .execute();
    }
}