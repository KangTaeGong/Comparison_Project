package project.reviews.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * 2023-02-16
 * 공통 적으로 사용되는 생성 일자
 * Auditing 기능을 포함 : 시간에 대해서 자동으로 값을 넣어주는 기능
 * Movie Entity는 수정 일자가 필요 없기 때문에 생성 일자만 따로 저장
 * */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CreatedTimeEntity {

    // Entity가 생성되서 저장될 때 시간이 자동 저장
    @CreatedDate
    private String createdDate;
}
