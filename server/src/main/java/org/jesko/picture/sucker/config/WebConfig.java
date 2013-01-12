package org.jesko.picture.sucker.config;

import java.io.IOException;

import javax.jmdns.JmDNS;

import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.jesko.picture.sucker"}, excludeFilters = {@ComponentScan.Filter(Configuration.class)})
public class WebConfig extends WebMvcConfigurerAdapter {
	
	public static final String JSON_PROFILE = "json";
	
	@Bean
	public JmDNS jmdns() throws IOException {
		return JmDNS.create();
	}
	
	@Configuration @Profile(value = JSON_PROFILE)
	static class Json {
		@Bean
		public ObjectMapper objectMapper() {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibility(JsonMethod.ALL, Visibility.ANY);
			return objectMapper;
		}
	}
}
