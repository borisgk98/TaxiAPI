package space.borisgk.taxi.api.configuration;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerMapperConfiguration {

    @Bean
    public Mapper dozenBeanMapper() {
        return DozerBeanMapperBuilder.buildDefault();
    }
}
