package indigo.hacktohire.email_smtp_client.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class MailTemplateConfiguration {

    @Primary
    @Bean
    public FreeMarkerConfigurationFactoryBean factoryBean(){
        FreeMarkerConfigurationFactoryBean configurationFactoryBean = new FreeMarkerConfigurationFactoryBean();
        configurationFactoryBean.setTemplateLoaderPath("classpath:/templates");
        return configurationFactoryBean;
    }

}