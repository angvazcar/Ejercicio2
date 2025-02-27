package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;

public class WikipediaPage {
    private WebDriver driver;

    public WikipediaPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getFirstYearFromPage() {
        String pageText = driver.findElement(By.tagName("body")).getText();
        String[] numeros = pageText.replaceAll("[^0-9]", " ").trim().split(" ");
        return (numeros.length > 0 && !numeros[0].isEmpty()) ? numeros[0] : "No encontrado";

    }
    public void getScreenshot(String nombreArchivo) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destino = new File("screenshot.png");
            Files.copy(screenshot.toPath(), destino.toPath());
            System.out.println(" Captura guardada en: " + destino.getAbsolutePath());
        } catch (Exception e) {
            System.out.println(" No se hizo captura de pantalla porque no se encontró la página");
        }
    }
    public void cerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }
}
