package space.borisgk.taxi.api;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public abstract class AbstractTestWithClosure {

    // Logger
    private final Logger logger = LoggerFactory.getLogger(AbstractTestWithClosure.class);


    // Exception handlers
    protected final Consumer<Exception> defaultEH = e -> {
        logger.error(e.getMessage());
        e.printStackTrace();
    };
    protected final Consumer<Exception> testEH = e -> {
        defaultEH.accept(e);
        Assert.fail();
    };

    protected interface TestConsumer {
        void exec() throws Exception;
    }

    /**
     * Exception wrapper
     * @param closure
     * @param exceptionHandler
     */
    protected void ew(TestConsumer closure, Consumer<Exception> exceptionHandler) {
        try {
            closure.exec();
        }
        catch (Exception e) {
            exceptionHandler.accept(e);
        }
    }


}

