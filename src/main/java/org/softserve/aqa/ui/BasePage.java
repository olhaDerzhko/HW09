package org.softserve.aqa.ui;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public class BasePage {
    protected WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public static final int SMALL_WAIT_TIME = 10;

    @FindBy(xpath = "//ul/li/span")
    private WebElement languageDropdown;

    @FindBy(xpath = "//li/span[contains(text(), 'En')]")
    private WebElement enLanguage;

    public void switchToEnLanguage() {
        log.info("Checking current language and switching to EN if necessary");
        getSmallWait().until(ExpectedConditions.visibilityOf(languageDropdown));

        String currentLangText = languageDropdown.getText().trim();
        if (currentLangText.equalsIgnoreCase("ua")) {
            log.info("Current language is Ukrainian. Switching to English.");
            languageDropdown.click();
            enLanguage.click();
        } else {
            log.info("Language is already English. No need to switch.");
        }
    }

    public void scrollToElementByJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView()", element);
    }

    public void clickOnElementByJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public WebDriverWait getSmallWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(SMALL_WAIT_TIME));
    }
}
