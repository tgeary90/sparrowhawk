package tom.app.engine;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import tom.app.engine.service.ListLoaderService;

@SpringBootApplication
public class SparrowhawkApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx = new SparrowhawkApplication()
		.configure(new SpringApplicationBuilder(
				SparrowhawkApplication.class
				)).run(args);
		
		// read in Managed Lists from disk
		//ctx.getBean(ListLoaderService.class).load();
	}
}
