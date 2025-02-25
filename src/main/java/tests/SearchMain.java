package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.GooglePage;
import pages.WikipediaPage;


public class SearchMain {
    public static void main(String[] args) {
        // Configurar ChromeDriver automáticamente
        WebDriverManager.chromedriver().setup();

        // Opciones de Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--start-maximized");

        // Iniciar WebDriver
        WebDriver driver = new ChromeDriver(options);

        try {
            GooglePage googlePage = new GooglePage(driver);
            googlePage.open();
            googlePage.acceptCookies();
            googlePage.search("automatización");

            // Buscar el enlace de Wikipedia y navegar a él
            if (googlePage.findWikipediaLink() != null) {
                googlePage.findWikipediaLink().click();
            } else {
                System.out.println("No se encontró un enlace de Wikipedia en los resultados.");
                return;
            }

            // Acceder a la página de Wikipedia
            WikipediaPage wikipediaPage = new WikipediaPage(driver);
            String yearFound = wikipediaPage.getFirstYearFromPage();
            System.out.println("El primer proceso automático se realizó en el año: " + yearFound);

            // Tomar captura de pantalla
            wikipediaPage.takeScreenshot("wikipedia_screenshot.png");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
