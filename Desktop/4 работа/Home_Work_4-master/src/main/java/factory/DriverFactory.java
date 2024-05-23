package factory;

import exceptions.BrowserNotSupportedException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class DriverFactory {

    private String browserName = "chrome";
    private String[] arguments;
    public DriverFactory(String... arguments) {
        this.arguments = arguments;
    }

    public WebDriver create() {
        browserName = browserName.toLowerCase();
        if (browserName.equals("chrome")) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments(arguments);
            return new ChromeDriver(chromeOptions);
        }
        throw new BrowserNotSupportedException(browserName);
    }
}
