package tom.app.engine;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SparrowhawkApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		new SparrowhawkApplication()
		
		.configure(new SpringApplicationBuilder(
				SparrowhawkApplication.class
				)).run(args);
	}
}
