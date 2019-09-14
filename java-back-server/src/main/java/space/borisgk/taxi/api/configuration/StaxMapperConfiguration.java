package space.borisgk.taxi.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import space.borisgk.taxi.api.model.mapping.StaxMapper;

@Configuration
public class StaxMapperConfiguration {

    @Bean
    public StaxMapper mapper() {
        return StaxMapper.instance;
    }
}
