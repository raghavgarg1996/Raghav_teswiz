package com.znsio.teswiz.screen.vodqa;

import com.znsio.teswiz.entities.Platform;
import com.znsio.teswiz.runner.Driver;
import com.znsio.teswiz.runner.Drivers;
import com.znsio.teswiz.runner.Runner;
import com.znsio.teswiz.runner.Visual;
import com.znsio.teswiz.screen.android.vodqa.AndroidHomeScreenAndroid;
import com.znsio.teswiz.screen.android.vodqa.VodqaScreenAndroid;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;

public abstract class AndroidHomeScreen {

    private static final String SCREEN_NAME = AndroidHomeScreen.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(SCREEN_NAME);

    public static AndroidHomeScreen get() {
        Driver driver = Drivers.getDriverForCurrentUser(Thread.currentThread().getId());
        Platform platform = Runner.fetchPlatform(Thread.currentThread().getId());
        LOGGER.info(SCREEN_NAME + ": Driver type: " + driver.getType() + ": Platform: " + platform);
        Visual visually = Drivers.getVisualDriverForCurrentUser(Thread.currentThread().getId());

        switch(platform) {
            case android:
                return new AndroidHomeScreenAndroid(driver, visually);
        }
        throw new NotImplementedException(
                SCREEN_NAME + " is not implemented in " + Runner.getPlatform());
    }


    public abstract validateAppWorkInBackground();

}