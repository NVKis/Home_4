import factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tools.Waiters;

public class HeadlessTest {
    private Logger logger = (Logger) LogManager.getLogger(HeadlessTest.class);
    private String duckUrl = System.getProperty("duck.url");
    private WebDriver driver;
    private Waiters waitTools;

    @BeforeEach
    public void init() {
        driver = new DriverFactory("--headless").create();
        waitTools = new Waiters(driver);
        driver.get(duckUrl);
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
    public void searchResult() {
        driver.findElement(By.cssSelector("input[aria-autocomplete=\"both\"]"))
                .sendKeys("отус" + Keys.ENTER);
        WebElement firstLinkEl = driver
                .findElement(By.xpath("//span[text()='Онлайн‑курсы для профессионалов, дистанционное обучение современным ...']"));
        logger.info("Wait results search");
        waitTools.waitForCondition(ExpectedConditions.stalenessOf(firstLinkEl));
        logger.info("Get text first link");
        String factResult = firstLinkEl.getText();
        logger.info("Check text");
        Assertions.assertEquals("Онлайн‑курсы для профессионалов, дистанционное обучение современным ...", factResult);
        logger.info("The text of the first link matches the expected result");
    }
}
