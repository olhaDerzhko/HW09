package org.softserve.aqa.ui.pages.homapage;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.softserve.aqa.ui.BasePage;
import org.softserve.aqa.ui.pages.autorizationpage.LoginPage;

@Slf4j
public class HomePage extends BasePage {

    @FindBy(xpath = "//ul/li/span")
    private WebElement languageDropdown;

    @FindBy(css = "div.main-content.app-container li.lang-option > span")
    private WebElement languageButton;

    @FindBy(xpath = "//li/span[contains(text(), 'En')]")
    private WebElement enLanguage;

    @FindBy(css = "img[alt='sing in button']")
    private WebElement loginButton;

    @FindBy(xpath = "//div[@class='form-input']/input")
    private WebElement emailSubscribeInput;

    @FindBy(xpath = "//div[@id='form-wrapper']/div/button")
    private WebElement subscribeButton;

    @FindBy(id = "validation-error")
    private WebElement validationErrorSubscribeMessage;

    @FindBy(css = "mat-mdc-tooltip-trigger name")
    private WebElement userAuthorizationName;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public HomePage switchToEnLanguage() {
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
        return new HomePage(driver);
    }

    public LoginPage openLoginPage() {
        clickOnElementByJS(loginButton);
        log.info("'Sign In' button clicked successfully");
        return new LoginPage(driver);
    }

    public String getTitle() {
        log.info("Fetching title text from the home page");
        return driver.getTitle();
    }

    public String getUserName() {
        log.info("Fetching user name text after successful authorization");
        return userAuthorizationName.getText();
    }

    public String getSubscriptionErrorMessage() {
        return validationErrorSubscribeMessage.getText().trim();
    }

    public HomePage fillEmailForSubscribe(String email) {
        scrollToElementByJS(emailSubscribeInput);
        getSmallWait().until(ExpectedConditions.visibilityOf(emailSubscribeInput));

        setFieldValue(emailSubscribeInput, email);
        log.info("Email '{}' for subscription has been entered successfully", email);
        clickOnSubscribeButton();
        return this;
    }

    public HomePage clickOnSubscribeButton() {
        subscribeButton.click();
        log.info("Subscribe button clicked successfully");
        return this;
    }

    public HomePage signOutApplication() {
        return new HomePage(driver);
    }
}
