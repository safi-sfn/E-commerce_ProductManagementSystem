package in.inxod.pms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//This class configures CORS (Cross-Origin Resource Sharing) and static resource handling
//@Configuration indicates this class contains Spring configuration settings
//@EnableWebMvc enables Spring MVC configuration
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

 // This method configures CORS settings to allow frontend applications to access the API
 @Override
 public void addCorsMappings(CorsRegistry registry) {
     registry.addMapping("/**")               // Apply CORS to all endpoints
             .allowedOrigins(
                 "http://localhost:5173",     // Allow Vite dev server (React/Vue)
                 "http://localhost:3000",     // Allow React dev server  
                 "http://localhost:4200"      // Allow Angular dev server
             )
             .allowedMethods("*")             // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
             .allowedHeaders("*");            // Allow all HTTP headers
 }
 
 // This method configures static resource handling for file uploads
 @Override
 public void addResourceHandlers(ResourceHandlerRegistry registry) {
     // Map URL path "/uploads/**" to serve files from the "./uploads/" directory
     registry.addResourceHandler("/uploads/**")
             .addResourceLocations("file:./uploads/");
 }
}
