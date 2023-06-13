package com.znsio.teswiz.screen.android.vodqa;

import com.znsio.teswiz.runner.Driver;
import com.znsio.teswiz.runner.Visual;
import com.znsio.teswiz.screen.vodqa.AndroidHomeScreen;

public class AndroidHomeScreenAndroid extends AndroidHomeScreen {
    private final Driver driver;
    private final Visual visually;
    private final String SCREEN_NAME = AndroidHomeScreenAndroid.class.getSimpleName();

    public AndroidHomeScreenAndroid(Driver driver, Visual visually) {
        this.driver = driver;
        this.visually = visually;
    }


}
