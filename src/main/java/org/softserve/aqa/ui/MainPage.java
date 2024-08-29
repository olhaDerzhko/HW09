package org.softserve.aqa.ui;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.softserve.aqa.ui.pages.BasePage;

@Slf4j
public class MainPage extends BasePage {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//ul/li/span")
    private WebElement languageDropdown;

    @FindBy(xpath = "//li/span[contains(text(), 'En')]")
    private WebElement enLanguage;

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
        signInButton.click();
    }

    public void switchToEnLanguage() {
        log.info("Checking current language and switching to EN if necessary");

        String currentLangText = languageDropdown.getText().trim();
        if (currentLangText.equalsIgnoreCase("ua")) {
            log.info("Current language is Ukrainian. Switching to English.");
            languageDropdown.click();
            enLanguage.click();
        } else {
            log.info("Language is already English. No need to switch.");
        }
    }

    public String getWelcomeText() {
        return welcomeText.getText();
    }

    public String getSignInDetailsText() {
        return signInDetailsText.getText();
    }

    public String getEmailText() {
        return emailInput.getAttribute("value");
    }

    public String getPasswordText() {
        return passwordInput.getAttribute("value");
    }

    public void enterEmail(String email) {
        emailInput.click();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickSubmitButton() {
        signInSubmitButton.click();
    }

    public String getErrorEmailText() {
        return errorEmail.getText();
    }

    public String getErrorPasswordText() {
        return errorPassword.getText();
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
