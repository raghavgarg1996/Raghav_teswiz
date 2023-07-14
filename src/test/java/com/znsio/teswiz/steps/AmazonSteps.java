package com.znsio.teswiz.steps;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import com.znsio.teswiz.businessLayer.amazon.AmazonHomeBL;
import io.cucumber.java.en.Given;
import org.apache.log4j.Logger;

public class AmazonSteps {

    private static final Logger LOGGER = Logger.getLogger(AmazonSteps.class.getName());
    private final TestExecutionContext context;

    public AmazonSteps() {

        context = SessionContext.getTestExecutionContext(Thread.currentThread().getId());
        LOGGER.info("context: " + context.getTestName());

    }

    @Given("I as a guest user perform scroll")
    public void iAsAGuestUserPerformScroll() {
        new AmazonHomeBL().scroll();
    }
}
