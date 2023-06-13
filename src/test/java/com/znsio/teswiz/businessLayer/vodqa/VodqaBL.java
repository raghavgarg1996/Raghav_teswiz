package com.znsio.teswiz.businessLayer.vodqa;

import com.context.TestExecutionContext;
import com.znsio.teswiz.entities.Platform;
import com.znsio.teswiz.entities.SAMPLE_TEST_CONTEXT;
import com.znsio.teswiz.runner.Runner;
import com.znsio.teswiz.screen.vodqa.VodqaScreen;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;

public class VodqaBL {

    private static final Logger LOGGER = Logger.getLogger(VodqaBL.class.getName());
    private final TestExecutionContext context;
    private final SoftAssertions softly;
    private final String currentUserPersona;
    private final Platform currentPlatform;

    public VodqaBL(String userPersona, Platform forPlatform) {
        long threadId = Thread.currentThread().getId();
        this.context = Runner.getTestExecutionContext(threadId);
        softly = Runner.getSoftAssertion(threadId);
        this.currentUserPersona = userPersona;
        this.currentPlatform = forPlatform;
        Runner.setCurrentDriverForUser(userPersona, forPlatform, context);
    }

    public VodqaBL() {
        long threadId = Thread.currentThread().getId();
        this.context = Runner.getTestExecutionContext(threadId);
        softly = Runner.getSoftAssertion(threadId);
        this.currentUserPersona = SAMPLE_TEST_CONTEXT.ME;
        this.currentPlatform = Runner.getPlatform();
    }

    public VodqaBL login() {
        VodqaScreen.get().login();
        return this;
    }

    public AndroidHomeBL navigateToHomeScreen() {
        VodqaScreen.get().clickOnHomeButton();
        return this;
    }
}