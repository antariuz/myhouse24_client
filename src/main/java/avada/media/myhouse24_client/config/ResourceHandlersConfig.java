package avada.media.myhouse24_client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.file.Paths;

@Configuration
public class ResourceHandlersConfig extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadPath;
        if (System.getProperty("os.name").contains("Linux")) {
            uploadPath = Paths.get("/home/tmp/myhouse24/").toFile().getAbsolutePath();
            registry.addResourceHandler("/uploaded/**").addResourceLocations("file://" + uploadPath + "/");
        } else {
            uploadPath = Paths.get("uploaded").toFile().getAbsolutePath();
            registry.addResourceHandler("/uploaded/**").addResourceLocations("file:/" + uploadPath + "/");
        }
        //        Static folder
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

}
