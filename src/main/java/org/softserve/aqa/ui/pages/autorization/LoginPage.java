package org.softserve.aqa.ui.pages.autorization;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.softserve.aqa.data.User;
import org.softserve.aqa.ui.BasePage;
import org.softserve.aqa.ui.pages.unauthorizeduser.homepage.HomePage;

@Slf4j
public class LoginPage extends BasePage {

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

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public HomePage successfullyLogin(User validUser) {
        log.info("Attempting to log in with email: {}", validUser.getEmail());
        setFieldValue(emailInput, validUser.getEmail());
        setFieldValue(passwordInput, validUser.getPassword());

        signInSubmitButton.click();
        log.info("User successfully logged in");
        return new HomePage(driver);
    }

    public LoginPage unsuccessfullyLogin (User validUser) {
        log.info("Attempting to log in with incorrect credentials: {}", validUser.getEmail());
        setFieldValue(emailInput, validUser.getEmail());
        setFieldValue(passwordInput, validUser.getPassword());

        signInSubmitButton.click();
        log.warn("Login failed due to incorrect credentials");
        return new LoginPage(driver);
    }

    public String getWelcomeText() {
        log.info("Fetching welcome text from the page");
        return welcomeText.getText();
    }

    public String getSignInDetailsText() {
        log.info("Fetching sign-in details text from the page");
        return signInDetailsText.getText();
    }

    public String getErrorEmailText() {
        log.info("Fetching error message for 'email'");
        return errorEmail.getText();
    }

    public String getErrorPasswordText() {
        log.info("Waiting for password error message to become visible");
        getSmallWait().until(ExpectedConditions.visibilityOf(errorPassword));

        return errorPassword.getText().trim();
    }
}
