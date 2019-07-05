package space.borisgk.taxi.api

import org.dozer.DozerBeanMapperBuilder
import org.dozer.Mapper
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
class KotlinBackServerApplication {

    @Bean
    fun dozenBeanMapper(): Mapper {
        return DozerBeanMapperBuilder.buildDefault()
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(KotlinBackServerApplication::class.java, *args)
}