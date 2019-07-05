package space.borisgk.taxi.api.aspect

import com.fasterxml.jackson.databind.ObjectMapper
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import space.borisgk.taxi.api.exception.IllegalAspectTargetException
import space.borisgk.taxi.api.exception.ServerException
import java.lang.reflect.InvocationTargetException

import java.lang.reflect.Method

@Component
@Aspect
class KafkaErrorHandlingAspect {

    @Autowired
    private val om: ObjectMapper? = null
    @Autowired
    private val kafkaTemplate: KafkaTemplate<String, String>? = null

    private val logger = LoggerFactory.getLogger(KafkaErrorHandlingAspect::class.java)

    @Pointcut("@annotation(org.springframework.kafka.annotation.KafkaListener)")
    private fun pointcut() {
    }

    @Around("pointcut()")
    @Throws(Throwable::class)
    private fun aroundAdvise(call: ProceedingJoinPoint) {
        if (call.args.size != 1) {
            logger.error("Illegal aspect target: method was annotated with @KafkaListener " +
                    "should accept only one argument (payload)")
            throw IllegalAspectTargetException()
        }
        logger.info("Receive payload: ")
        logger.info(call.args[0].toString())
        try {
            call.proceed()
        } catch (e: ServerException) {
            val signature = call.signature as MethodSignature
            val method: Method = signature.method
            logger.error("Error while call method: " + method.declaringClass + "." + method.name)
            logger.error(e.message)
            try {
                kafkaTemplate!!.send("error", e.message)
            } catch (e2: Exception) {
                logger.error("Error while send error message")
            }
        }

    }
}
