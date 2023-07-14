package com.znsio.teswiz.screen.web.amazon;

import com.znsio.teswiz.runner.Driver;
import com.znsio.teswiz.runner.Visual;
import com.znsio.teswiz.screen.amazon.AmazonHomeScreen;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

public class AmazonHomeScreenWeb extends AmazonHomeScreen {
    private static final String SCREEN_NAME = AmazonHomeScreenWeb.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(SCREEN_NAME);
    private static final String NOT_YET_IMPLEMENTED = " not yet implemented";
    private final Driver driver;
    private final Visual visually;

    public AmazonHomeScreenWeb(Driver driver, Visual visually) {
        this.driver = driver;
        this.visually = visually;
        visually.checkWindow(SCREEN_NAME, "Launch screen");
    }

    @Override
    public AmazonHomeScreen performScroll() {
        driver.waitTillElementIsPresent(By.id("pageContent"),5);
        driver.scrollToBottom();
        visually.checkWindow(SCREEN_NAME, "Capturing scrollBar detao");

  //      visually.
        return this;
    }
}
