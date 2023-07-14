package com.znsio.teswiz.businessLayer.amazon;

import com.context.TestExecutionContext;
import com.znsio.teswiz.businessLayer.ajio.HomeBL;
import com.znsio.teswiz.entities.Platform;
import com.znsio.teswiz.entities.SAMPLE_TEST_CONTEXT;
import com.znsio.teswiz.runner.Runner;
import com.znsio.teswiz.screen.amazon.AmazonHomeScreen;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;

public class AmazonHomeBL {

    private static final Logger LOGGER = Logger.getLogger(AmazonHomeBL.class.getName());
    private final TestExecutionContext context;
    private final SoftAssertions softly;
    private final String currentUserPersona;
    private final Platform currentPlatform;

    public AmazonHomeBL(String userPersona, Platform forPlatform) {
        long threadId = Thread.currentThread().getId();
        this.context = Runner.getTestExecutionContext(threadId);
        softly = Runner.getSoftAssertion(threadId);
        this.currentUserPersona = userPersona;
        this.currentPlatform = forPlatform;
        Runner.setCurrentDriverForUser(userPersona, forPlatform, context);
    }

    public AmazonHomeBL() {
        long threadId = Thread.currentThread().getId();
        this.context = Runner.getTestExecutionContext(threadId);
        softly = Runner.getSoftAssertion(threadId);
        this.currentUserPersona = SAMPLE_TEST_CONTEXT.ME;
        this.currentPlatform = Runner.getPlatform();
    }

    public AmazonHomeBL scroll() {
        AmazonHomeScreen.get().performScroll();
        return this;
    }
}
