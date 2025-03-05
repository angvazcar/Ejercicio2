package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class WikipediaPage {
    private WebDriver driver;

    public WikipediaPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getFirstYearFromPage() {
        String pageText = driver.findElement(By.tagName("body")).getText();
        String[] numeros = pageText.replaceAll("[^0-9]", " ").trim().split(" ");
        String firstYear = (numeros.length > 0 && !numeros[0].isEmpty()) ? numeros[0] : "No encontrado";

        System.out.println("El primer año encontrado en la página es: " + firstYear);
        return firstYear;
    }

    public void getScreenshot(String nombreArchivo) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File destino = new File("screenshots/" + nombreArchivo + "_" + timestamp + ".png");

            // Crear carpeta 'screenshots' si no existe
            destino.getParentFile().mkdirs();

            // Copiar la captura al destino sin sobrescribir
            Files.copy(screenshot.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Confirmación en consola
            System.out.println("Captura guardada en: " + destino.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("No se hizo captura de pantalla porque no se encontró la página o hubo un error.");
            e.printStackTrace();
        }
    }

    public void cerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }
}
