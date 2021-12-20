import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TodoTest {

    WebDriver driver;

    @BeforeEach
    public void initialiseDriver() throws  InterruptedException{
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }


    @ParameterizedTest
    @CsvSource({"eat,sleep,game,repeat", "eat,sleep,code,repeat"})
    public void test(ArgumentsAccessor argumentsAccessor) throws  InterruptedException{
        driver.get("https://todomvc.com");
        Thread.sleep(500);
        //open Backbone.js
        driver.findElement(By.linkText("Backbone.js")).click();
        Thread.sleep(500);
        // add tasks
        for (int i = 0;i < argumentsAccessor.size();i++){
            driver.findElement(By.cssSelector(".new-todo")).sendKeys(argumentsAccessor.getString(i));
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".new-todo")).sendKeys(Keys.ENTER);
            Thread.sleep(500);
        }
        // select element
        driver.findElement(By.cssSelector("li:nth-child(3) .toggle")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("li:nth-child(3) .toggle")).click();
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("/html/body/section/footer/span/strong")), Integer.toString(argumentsAccessor.size())));
    }

    @AfterEach
    public void quitDriver() throws InterruptedException {
        Thread.sleep(1000);
        driver.quit();
    }
}