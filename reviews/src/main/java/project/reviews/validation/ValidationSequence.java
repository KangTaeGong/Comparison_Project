package project.reviews.validation;

import project.reviews.login.JoinValidator;
import project.reviews.validation.ValidationGroups.NotEmptyGroup;
import project.reviews.validation.ValidationGroups.PatternCheckGroup;
import javax.validation.GroupSequence;

@GroupSequence({NotEmptyGroup.class, PatternCheckGroup.class})
public interface ValidationSequence {
}
