package test;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.cucumber.java.en.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class BusquedaGoogleSteps {
    private static final Logger logger = LoggerFactory.getLogger(BusquedaGoogleSteps.class);
    public static WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private By acceptCookiesButton = By.id("L2AGLb");
    private By searchBox = By.name("q");
    private pages.WikipediaPage wikipediaPage = new pages.WikipediaPage(driver);


    @Given("que el usuario abre el navegador y navega a Google")
    public void abrirNavegadorYBuscarGoogle() {

        // Configuración de ChromeOptions para evitar detección
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled"); // Evita detección de bot
        options.addArguments("--disable-infobars"); // Evita mensajes de "controlado por software de pruebas"


        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        actions = new Actions(driver);

        driver.get("https://www.google.com");
        pages.GooglePage googlePage = new pages.GooglePage(driver);
        // Manejo del pop-up de cookies
        googlePage.acceptCookies();
    }


    @When("el usuario ingresa {string} en la barra de búsqueda")
    public void ingresarTerminoEnBarraDeBusqueda(String termino) {
        pages.GooglePage googlePage = new pages.GooglePage(driver);
        googlePage.buscarTermino(termino);

    }

    @When("hace clic en el botón de búsqueda")
    public void clicEnBotonDeBusqueda() {
        WebElement barraBusqueda = wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        // Presionar Enter para buscar
        barraBusqueda.sendKeys(Keys.RETURN);

    }

    @Then("el usuario hace clic en el enlace de Wikipedia")
    public void clicEnEnlaceDeWikipedia() {
        try {
            // Espera hasta que aparezca el enlace de Wikipedia
            WebElement enlaceWikipedia = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Wikipedia")));
            // Hacer click en el enlace
            enlaceWikipedia.click();
            logger.info("Accediste a Wikipedia.");
        } catch (Exception e) {
            logger.warn("No se encontró el enlace de Wikipedia.");
        }
    }


    @Then("extrae el primer año mencionado en la página")
    public void extraerPrimerAnoMencionado() {
        pages.WikipediaPage wikipediaPage = new pages.WikipediaPage(driver);
        String yearFound = wikipediaPage.getFirstYearFromPage();
        if (yearFound !=null){
            logger.info("El primer proceso automático se realizó en el año: " + yearFound);
        }else{
            logger.warn("WikipediaPage no se encontró.");
        }
    }

    @Then("toma una captura de pantalla de la página de Wikipedia")
    public void tomarCapturaDePantalla() throws Exception {
        pages.WikipediaPage wikipediaPage = new pages.WikipediaPage(driver);
        wikipediaPage.getScreenshot("screenshot.png");
        wikipediaPage.cerrarNavegador();
    }
}

