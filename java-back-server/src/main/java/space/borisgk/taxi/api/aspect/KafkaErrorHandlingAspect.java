package space.borisgk.taxi.api.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import space.borisgk.taxi.api.exception.IllegalAspectTargetException;
import space.borisgk.taxi.api.exception.ServerException;

import java.lang.reflect.Method;

@Component
@Aspect
public class KafkaErrorHandlingAspect {

    @Autowired
    private ObjectMapper om;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Logger logger = LoggerFactory.getLogger(KafkaErrorHandlingAspect.class);

    @Pointcut("@annotation(org.springframework.kafka.annotation.KafkaListener)")
    private void pointcut() {}

    @Around("pointcut()")
    private void aroundAdvise(ProceedingJoinPoint call) throws Throwable{
        if (call.getArgs().length != 1) {
            logger.error("Illegal aspect target: method was annotated with @KafkaListener " +
                    "should accept only one argument (payload)");
            throw new IllegalAspectTargetException();
        }
        String payload = call.getArgs()[0].toString();
        logger.info("Receive payload: ");
        logger.info(payload);

        kafkaTemplate.send("response.test", payload);

        try {
            call.proceed();
        } catch (ServerException e) {
            MethodSignature signature = (MethodSignature) call.getSignature();
            Method method = signature.getMethod();
            logger.error("Error while call method: " + method.getDeclaringClass() + "." + method.getName());
            logger.error(e.getMessage());
            try {
                kafkaTemplate.send("error", e.getMessage());
            } catch (Exception e2) {
                logger.error("Error while send error message");
            }
        }
    }
}
