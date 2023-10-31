package com.znsio.teswiz.steps;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import com.znsio.teswiz.businessLayer.helloWorld.HelloWorldBL;
import com.znsio.teswiz.entities.SAMPLE_TEST_CONTEXT;
import com.znsio.teswiz.runner.Drivers;
import com.znsio.teswiz.runner.Runner;
import io.cucumber.java.en.Given;
import org.apache.log4j.Logger;

public class HelloWorldSteps {

    private static final Logger LOGGER = Logger.getLogger(HelloWorldSteps.class.getName());
    private final TestExecutionContext context;

    public HelloWorldSteps() {
        context = SessionContext.getTestExecutionContext(Thread.currentThread().getId());
        LOGGER.info("context: " + context.getTestName());
    }

    @Given("I  press Click Me button")
    public void iPressClickMeButton() {
        LOGGER.info(System.out.printf("iPress - Persona:'%s'", SAMPLE_TEST_CONTEXT.ME));
        Drivers.createDriverFor(SAMPLE_TEST_CONTEXT.ME, Runner.getPlatform(), context);
        new HelloWorldBL(SAMPLE_TEST_CONTEXT.ME, Runner.getPlatform()).clickButton();
    }
}
