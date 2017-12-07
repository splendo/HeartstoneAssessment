package nl.tom.hstone;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Required by the GAE Standard (webapp) deployment.
 * 
 * @author Tom
 *
 */
public class ServletInitializer extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(HeartstoneAssessmentSpringApplication.class);
	}
}