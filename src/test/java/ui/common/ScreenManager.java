package ui.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.softserve.aqa.utils.Constants.SCREENSHOT_EXTENSION;
import static org.softserve.aqa.utils.Constants.SOURCE_HTML_EXTENSION;

@Slf4j
public class ScreenManager {
    private static final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss-S";
    private final WebDriver driver;
    private final String screenshotsFolder;
    private final String sourceHtmlFolder;

    protected ScreenManager(WebDriver driver, String screenshotsFolder, String sourceHtmlFolder) {
        this.driver = driver;
        this.screenshotsFolder = screenshotsFolder;
        this.sourceHtmlFolder = sourceHtmlFolder;

        createFolders(screenshotsFolder, sourceHtmlFolder);
    }

    private void createFolders(String... folderPaths) {
        for (String folderPath : folderPaths) {
            try {
                Files.createDirectories(Paths.get(folderPath));
            } catch (IOException e) {
                throw new RuntimeException("Failed to create directory: " + folderPath, e);
            }
        }
    }

    public void takeScreenshot(String screenshotName) {
        log.debug("Start takeScreenShot()");

        TakesScreenshot screenshot = (TakesScreenshot) driver;
        byte[] screenShotAtBytes = screenshot.getScreenshotAs(OutputType.BYTES);

        String screenShotFileName = String.format("%s - %s%s", screenshotName, getFormattedTime(), SCREENSHOT_EXTENSION);
        File screenshotFile = new File(screenshotsFolder, screenShotFileName);

        try {
            FileUtils.writeByteArrayToFile(screenshotFile, screenShotAtBytes);
            log.info("Screenshot saved: {}", screenshotFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("Failed to save screenshot: {}", e.getMessage());
        }
    }

    public void takePageSource(String sourceHtmlName) {
        log.debug("Start takePageSource()");

        String sourceHtmlFileName = String.format("%s - %s%s", sourceHtmlName, getFormattedTime(), SOURCE_HTML_EXTENSION);
        Path path = Paths.get(sourceHtmlFolder, sourceHtmlFileName);

        try {
            Files.write(path, driver.getPageSource().getBytes(), StandardOpenOption.CREATE);
            log.info("Page source saved: {}", path.toAbsolutePath());
        } catch (IOException e) {
            log.error("Failed to save page source: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String getFormattedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_TEMPLATE);
        return LocalDateTime.now().format(formatter);
    }
}
