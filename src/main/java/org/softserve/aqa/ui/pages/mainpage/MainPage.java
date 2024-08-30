package org.softserve.aqa.ui.pages.mainpage;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.softserve.aqa.ui.BasePage;

@Slf4j
public class MainPage extends BasePage {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
