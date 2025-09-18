package in.inxod.pms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

	  @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	                .allowedOrigins("http://localhost:5173", "http://localhost:3000", "http://localhost:4200")
	                .allowedMethods("*")
	                .allowedHeaders("*");
	    }
	
	  @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	      
	        registry.addResourceHandler("/uploads/**")
	                .addResourceLocations("file:./uploads/");
	    }
}
