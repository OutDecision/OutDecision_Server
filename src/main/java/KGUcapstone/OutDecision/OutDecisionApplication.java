package KGUcapstone.OutDecision;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OutDecisionApplication {

	public static void main(String[] args) {
		SpringApplication.run(OutDecisionApplication.class, args);
	}

}
