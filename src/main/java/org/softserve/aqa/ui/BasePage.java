package org.softserve.aqa.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasePage {
    protected WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(xpath = "//ul/li/span")
    private WebElement languageDropdown;

    @FindBy(xpath = "//li/span[contains(text(), 'En')]")
    private WebElement enLanguage;

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
}
