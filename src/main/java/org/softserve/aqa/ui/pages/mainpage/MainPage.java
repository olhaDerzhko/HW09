package org.softserve.aqa.ui.pages.mainpage;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.softserve.aqa.ui.BasePage;

@Slf4j
public class MainPage extends BasePage {
    @FindBy(xpath = "//div[@class='form-input']/input")
    private WebElement emailSubscribeInput;

    @FindBy(xpath = "//div[@id='form-wrapper']/div/button")
    private WebElement subscribeButton;

    @FindBy(id="validation-error")
    private WebElement validationErrorSubscribeMessage;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        log.info("Fetching title text from the main page");
        return driver.getTitle();
    }

    public String getSubscriptionErrorMessage() {
        getSmallWait().until(ExpectedConditions.visibilityOf(validationErrorSubscribeMessage));
        return validationErrorSubscribeMessage.getText().trim();
    }

    public void fillEmailForSubscribe(String email) {
        scrollToElementByJS(emailSubscribeInput);
        getSmallWait().until(ExpectedConditions.visibilityOf(emailSubscribeInput));

        emailSubscribeInput.click();
        emailSubscribeInput.sendKeys(email);
        log.info("Email '{}' for subscription has been entered successfully", email);
    }

    public void clickOnSubscribeButton() {
        subscribeButton.click();
        log.info("Subscribe button clicked successfully");
    }
}
