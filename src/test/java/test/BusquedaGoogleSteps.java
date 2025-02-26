package test;

import java.io.File;
import java.nio.file.Files;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import io.cucumber.java.en.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class BusquedaGoogleSteps {
    public static WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private Random random = new Random();
    private By searchResults = By.cssSelector("h3");
    private By acceptCookiesButton = By.id("L2AGLb");
    private By searchBox = By.name("q");

    @Given("que el usuario abre el navegador y navega a Google")
    public void abrirNavegadorYBuscarGoogle() {
        WebDriverManager.chromedriver().setup();

        // Configuración de ChromeOptions para evitar detección
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled"); // Evita detección de bot
        options.addArguments("--disable-infobars"); // Evita mensajes de "controlado por software de pruebas"


        driver = new ChromeDriver(options);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.actions = new Actions(driver);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.google.com");

        // Emular movimiento aleatorio del mouse antes de interactuar
        moverMouseAleatoriamente();
        pausaAleatoria();

        // Manejo del pop-up de cookies
        aceptarCookies();
    }

    @When("el usuario ingresa {string} en la barra de búsqueda")
    public void ingresarTerminoEnBarraDeBusqueda(String termino) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement barraBusqueda = wait.until(ExpectedConditions.elementToBeClickable(searchBox));

        // Mover el mouse sobre la barra de búsquedan(CAPTCHA)
        moverMouseSobreElemento(barraBusqueda);
        pausaAleatoria();

        // Hacer click en la barra de búsqueda(CAPTCHA)
        actions.moveToElement(barraBusqueda).click().perform();


        // Escribir el término de búsqueda letra por letra si no me aparece CAPTCHA
        for (char letra : termino.toCharArray()) {
            barraBusqueda.sendKeys(String.valueOf(letra));
        }

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
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Espera hasta que aparezca el enlace de Wikipedia
            WebElement enlaceWikipedia = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Wikipedia")));

            // Hacer click en el enlace
            enlaceWikipedia.click();
            System.out.println("Accediste a Wikipedia.");
        } catch (Exception e) {
            System.out.println("No se encontró el enlace de Wikipedia.");
        }
    }


    @Then("extrae el primer año mencionado en la página")
    public void extraerPrimerAnoMencionado() {
        pages.WikipediaPage wikipediaPage = new pages.WikipediaPage(driver);
        String yearFound = wikipediaPage.getFirstYearFromPage();
        System.out.println("El primer proceso automático se realizó en el año: " + yearFound);
    }

    @Then("toma una captura de pantalla de la página de Wikipedia")
    public void tomarCapturaDePantalla() throws Exception {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destino = new File("screenshot.png");
        Files.copy(screenshot.toPath(), destino.toPath());
        System.out.println(" Captura guardada en: " + destino.getAbsolutePath());
    }

    /**
     * Método para aceptar cookies si aparece el pop-up.
     */
    private void aceptarCookies() {
        try {
            WebElement botonAceptarCookies = wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton));
            moverMouseSobreElemento(botonAceptarCookies);
            pausaAleatoria();
            botonAceptarCookies.click();
            System.out.println("Cookies aceptadas.");
        } catch (Exception e) {
            System.out.println("No se encontró pop-up de cookies.");
        }
    }

    /**
     * Método para mover el mouse aleatoriamente en la pantalla.
     */
    private void moverMouseAleatoriamente() {
        int x = random.nextInt(800) + 100;
        int y = random.nextInt(600) + 100;
        actions.moveByOffset(x, y).perform();
    }

    /**
     * Método para mover el mouse sobre un elemento antes de hacer clic.
     */
    private void moverMouseSobreElemento(WebElement elemento) {
        actions.moveToElement(elemento).perform();
    }

    /**
     * Método para hacer una pausa aleatoria entre acciones.
     */
    private void pausaAleatoria() {
        try {
            Thread.sleep(random.nextInt(2000) + 500); // Entre 500ms y 2500ms
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Método para hacer un scroll aleatorio en la página.
     */
    private void scrollAleatorio() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int scrollY = random.nextInt(500) + 100;
        js.executeScript("window.scrollBy(0," + scrollY + ")");
    }
}
