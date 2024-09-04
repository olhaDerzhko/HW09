package org.softserve.aqa.ui.pages.autorizationpage;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.softserve.aqa.ui.BasePage;

@Slf4j
public class SignInPage extends BasePage {

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "img[alt='sing in button']")
    private WebElement signInButton;

    @FindBy(css = ".container  h1")
    private WebElement welcomeText;

    @FindBy(css = ".container  h2")
    private WebElement signInDetailsText;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//form/button[@class='greenStyle']")
    private WebElement signInSubmitButton;

    @FindBy(css = ".mat-simple-snackbar > span")
    private WebElement result;

    @FindBy(xpath = "//div[@id='pass-err-msg']/app-error/div")
    private WebElement errorPassword;

    @FindBy(xpath = "//div[@id='email-err-msg']/app-error/div")
    private WebElement errorEmail;

    public void clickSignInButton() {
        clickOnElementByJS(signInButton);
        log.info("'Sign In' button clicked successfully");
    }

    public String getWelcomeText() {
        log.info("Fetching welcome text from the page");
        return welcomeText.getText();
    }

    public String getSignInDetailsText() {
        log.info("Fetching sign-in details text from the page");
        return signInDetailsText.getText();
    }

    public String getEmailText() {
        log.info("Fetching the email value entered in the 'email' field");
        return emailInput.getAttribute("value");
    }

    public String getPasswordText() {
        log.info("Fetching the password value entered in the 'password' field");
        return passwordInput.getAttribute("value");
    }

    public void enterEmail(String email) {
        log.info("Filling 'email' field with value: {}", email);
        getSmallWait().until(ExpectedConditions.visibilityOf(emailInput));

        emailInput.click();
        emailInput.sendKeys(email);
        log.info("Email '{}' has been entered successfully", email);
    }

    public void enterPassword(String password) {
        log.info("Filling 'password' field");

        passwordInput.clear();
        passwordInput.sendKeys(password);
        log.info("Password has been entered successfully (masked for security)");
    }

    public void clickSubmitButton() {
        log.info("Click on the 'Submit' button");
        getSmallWait().until(ExpectedConditions.elementToBeClickable(passwordInput));

        clickOnElementByJS(signInSubmitButton);
    }

    public String getErrorEmailText() {
        log.info("Fetching error message for 'email'");
        return errorEmail.getText();
    }

    public String getErrorPasswordText() {
        log.info("Fetching error message for 'password'");
        return errorPassword.getText();
    }
}
