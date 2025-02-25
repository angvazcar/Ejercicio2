package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class GooglePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By searchBox = By.name("q");
    private By acceptCookiesButton = By.id("L2AGLb");
    private By searchResults = By.cssSelector("h3");

    public  GooglePage (WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void open() {
        driver.get("https://www.google.com");
    }

    //Gestionar las cookies que aparecen en la pagina de google
    public void acceptCookies() {
        try {
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton));
            acceptButton.click();
        } catch (TimeoutException e) {
            System.out.println("No se encontró el botón de aceptar cookies.");
        }
    }

    public void search(String query) {
        WebElement searchBoxElement = wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        Actions actions = new Actions(driver);
        actions.moveToElement(searchBoxElement).click().sendKeys(query).sendKeys(Keys.RETURN).perform();
    }

    public WebElement findWikipediaLink() {
        List<WebElement> results = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(searchResults));
        for (WebElement result : results) {
            if (result.getText().contains("Wikipedia")) {
                return result.findElement(By.xpath("./ancestor::a"));
            }
        }
        return null;
    }
}

