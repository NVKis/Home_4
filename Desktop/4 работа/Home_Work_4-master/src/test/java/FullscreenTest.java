import factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tools.Waiters;

import java.util.List;

public class FullscreenTest {
    private Logger logger = (Logger) LogManager.getLogger(FullscreenTest.class);
    private String layoutUrl = System.getProperty("layout.url");
    private WebDriver driver;
    private Waiters waitTools;

    @BeforeEach
    public void init() {
        driver = new DriverFactory("--kiosk").create();
        waitTools = new Waiters(driver);
        driver.get(layoutUrl);
        logger.info("Start driver");
    }

    @AfterEach
    public void stopDriver() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Stop driver");
    }

    @Test
    public void modalWindow() {

        List<WebElement> imgList = driver.findElements(By.cssSelector(".content-overlay"));

        logger.info("Picture found");

        waitTools.waitForCondition(ExpectedConditions.stalenessOf(imgList.get(3)));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView()", imgList.get(3));
        waitTools.waitForCondition(ExpectedConditions.elementToBeClickable(imgList.get(3)));
        imgList.get(3).click();
        WebElement modalWindowEl = driver.findElement(By.cssSelector(".pp_hoverContainer"));

        logger.info("Pop-up found");

        waitTools.waitForCondition(ExpectedConditions.stalenessOf(modalWindowEl));
        boolean factResult = driver.findElement(By.cssSelector(".pp_pic_holder.light_rounded")).isDisplayed();
        Assertions.assertTrue(factResult);

        logger.info("The comparison results are the same");
        logger.info("Test passed successfully");
    }
}
