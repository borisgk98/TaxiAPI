package space.borisgk.taxi.api.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import space.borisgk.taxi.api.AbstractTestWithClosure;
import space.borisgk.taxi.api.configuration.DozerMapperConfiguration;
import space.borisgk.taxi.api.model.dto.UserDto;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka
@Import(DozerMapperConfiguration.class)
public class UserConsumerTest extends AbstractTestWithClosure {

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    UserConsumer userConsumer;

    @Before
    public void setUp() {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.consumerProps("server-java", "false", embeddedKafkaBroker));
    }

    @Test
    public void userData() {
        ew(() -> {
           userConsumer.userData(mapper.writeValueAsString(UserDto.builder().firstName("Boris").lastName("Kozh").build()));
        }, testEH);
    }
}