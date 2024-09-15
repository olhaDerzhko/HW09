package org.softserve.aqa.ui;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait webDriverWait;
    protected JavascriptExecutor javascriptExecutor;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        javascriptExecutor = (JavascriptExecutor) driver;
    }

    public void scrollToElementByJS(WebElement element) {
        log.info("Scrolling to element: {}", element);
        javascriptExecutor.executeScript("arguments[0].scrollIntoView()", element);
        log.info("Scrolled to element successfully.");
    }

    public void clickOnElementByJS(WebElement element) {
        log.info("Attempting to click on element using JavaScript: {}", element);
        javascriptExecutor.executeScript("arguments[0].click();", element);
        log.info("Element clicked successfully using JavaScript.");
    }

    public WebDriverWait getSmallWait() {
        return webDriverWait;
    }

    protected void setFieldValue(WebElement input, String value) {
        try {
            log.info("Attempting to set value '{}' to input field: {}", value, input);
            input.click();
            input.clear();
            input.sendKeys(value);
            log.info("Value '{}' set successfully to the input field.", value);
        } catch (StaleElementReferenceException | TimeoutException e) {
            log.error("Failed to set value '{}' to the input field. Exception: {}", value, e.getMessage());
        }
    }
}

