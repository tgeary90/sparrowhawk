package tom.app.engine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class SparrowhawkConfig {

	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
	    CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
	    crlf.setIncludeClientInfo(true);
	    crlf.setIncludeQueryString(true);
	    crlf.setIncludePayload(true);
	    crlf.setMaxPayloadLength(1024);
	    return crlf;
	}
}
