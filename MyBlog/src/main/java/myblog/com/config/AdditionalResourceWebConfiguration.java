package myblog.com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Spring の設定クラス（配置类）
@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer{
												//Spring MVCの設定をカスタマイズ（接口）
	 @Override
	 //addResourceHandlers(ResourceHandlerRegistry registry): このメソッドは、静的リソースのリソースハンドラーを追加するために使用
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 //								//名前										//場所
	        registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
	    }
}
