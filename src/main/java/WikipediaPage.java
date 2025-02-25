package pages;
import java.io.File;

import org.openqa.selenium.*;

public class WikipediaPage {
    private WebDriver driver;

    public WikipediaPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getFirstYearFromPage() {
        String pageText = driver.findElement(By.tagName("body")).getText();
        return pageText.replaceAll("[^0-9]", " ").trim().split(" ")[0];
    }

    public void takeScreenshot(String filePath) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(filePath);
            screenshot.renameTo(destinationFile);
            System.out.println("Captura de pantalla guardada en: " + destinationFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("No se pudo guardar la captura de pantalla.");
        }
    }
}
