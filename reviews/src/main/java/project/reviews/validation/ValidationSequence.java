package project.reviews.validation;

import project.reviews.validation.ValidationGroups.NotEmptyGroup;
import project.reviews.validation.ValidationGroups.PatternCheckGroup;
import javax.validation.GroupSequence;

/*
* 2022-12-28
* 검증 우선순위를 정하기 위해 groupSequence 사용
* */
@GroupSequence({NotEmptyGroup.class, PatternCheckGroup.class})
public interface ValidationSequence {
}
