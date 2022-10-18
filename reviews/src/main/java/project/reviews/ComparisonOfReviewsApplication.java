package project.reviews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableJpaAuditing
public class ComparisonOfReviewsApplication {

	// Spring Security는 비밀번호 보안 때문에 BCryptPasswordEncoder를 반드시 사용해야 한다.
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(ComparisonOfReviewsApplication.class, args);
	}

}
