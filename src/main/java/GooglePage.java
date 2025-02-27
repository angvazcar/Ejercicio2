package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class GooglePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By acceptCookiesButton = By.id("L2AGLb");
    private By searchBox = By.name("q");


    public GooglePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
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

    public void buscarTermino(String termino){
    WebElement barraBusqueda = wait.until(ExpectedConditions.elementToBeClickable(searchBox));

    // Escribir el término de búsqueda letra por letra si no me aparece CAPTCHA
        for (char letra : termino.toCharArray()) {
        barraBusqueda.sendKeys(String.valueOf(letra));
        }
    }
}

