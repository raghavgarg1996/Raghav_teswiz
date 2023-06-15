package com.znsio.teswiz.steps;


import com.znsio.teswiz.businessLayer.vodqa.VodqaBL;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.log4j.Logger;
import com.context.TestExecutionContext;
import com.context.SessionContext;
import com.znsio.teswiz.runner.Drivers;
import com.znsio.teswiz.runner.Runner;
import com.znsio.teswiz.entities.SAMPLE_TEST_CONTEXT;

public class VodQASteps {

    private static final Logger LOGGER = Logger.getLogger(VodQASteps.class.getName());
    private final TestExecutionContext context;

    public VodQASteps() {
        context = SessionContext.getTestExecutionContext(Thread.currentThread().getId());
        LOGGER.info("context: " + context.getTestName());
    }

    @Given("I login to vodqa application using credentials")
    public void loginToApplication() {
        Drivers.createDriverFor(SAMPLE_TEST_CONTEXT.ME, Runner.getPlatform(), context);
        new VodqaBL(SAMPLE_TEST_CONTEXT.ME, Runner.getPlatform()).login();
    }


    @Then("App should work in background for {int} sec")
    public void appShouldWorkInBackgroundForDefinedTime(int time) {
        new VodqaBL().appWorksInBackground(time);
    }


}
