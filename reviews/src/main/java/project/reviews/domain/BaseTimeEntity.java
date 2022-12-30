package project.reviews.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
* 2022-09-27
* 공통 적으로 사용되는 생성 일자와, 수정 일자
* Auditing 기능을 포함 : 시간에 대해서 자동으로 값을 넣어주는 기능
* */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    // Entity가 생성되서 저장될 때 시간이 자동 저장
    @CreatedDate
    private String createdDate;

    // 조회한 Entity 값을 변경할 때 시간이 자동 저장
    @LastModifiedDate
    private String modifiedDate;

    /*
    * 엔티티를 저장하기 이전에 실행
    * 시간을 가져온 뒤 알맞게 포멧
    * */
    @PrePersist
    public void onPrePersist() {
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.modifiedDate = this.createdDate;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
