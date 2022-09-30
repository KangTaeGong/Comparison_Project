package project.reviews.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.reviews.repository.PostingRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PostingService {

    private final PostingRepository postingRepository;

}
