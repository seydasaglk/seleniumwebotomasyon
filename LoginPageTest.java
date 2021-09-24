import java.util.concurrent.TimeUnit; // Aralara bekleme konulması otomasyonda buton bulunması, site açılımı gibi işlemler için yapılmaktadır. Sıralı işlemler üst üste binmemelidir.
import org.junit.Assert; // Assertion olası beklenen-gerçekleşen sonuçların karşılaştırılması için kullanılır
import org.junit.Test;
import org.openqa.selenium.By; // Element bulunması için package dosyasıyla bağlantıyı sağlamak amacıyla ‘By’ tanımlaması yapılmalıdır.
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.junit.Assert.assertEquals;

public class LoginPageTest {

    @Test
    public void setupDriver() {

        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Seyda\\Desktop\\test\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();


        driver.get(link);
        assertEquals(link, "https://www.gittigidiyor.com/"); // Ana sayfanın açılıp açılmadığı kontrol edilir.

        driver.findElement(By.xpath(login)).click();
        driver.findElement(By.xpath(loginArea)).click(); // Login sayfası için gerekli id’ler package kısmında yapılmıştır.

        driver.findElement(By.id(EmailId)).sendKeys(EmailData);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS); // Girilmesi istenilen data girişi için zaman verilmektedir.
        driver.findElement(By.id(PasswordId)).sendKeys(PasswordData);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS); // İstenilen data girişi için zaman verilmektedir.
        driver.findElement(By.id(LoginId)).click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS); // Data işlemesi için zaman verilmektedir.

        Assert.assertEquals("GittiGidiyor", driver.getTitle()); // Login işlemi kontrol edilir.

        driver.findElement(By.xpath(SearchXpath)).click();
        driver.findElement(By.xpath(SearchXpath)).sendKeys(SearchData); //Kullanıcıdan bilgisayar kelimesinin girilmesi beklenir.
        driver.findElement(By.xpath(FindButtonXpath)).click();

        WebElement ele = driver.findElement(By.xpath(SecondPage)); // 2.sayfanın açılması beklenir.
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[2].click()", ele);

        driver.findElement(By.xpath(SelectProduct)).click(); // Rastgele ürün seçimi gerçekleşir.
        String productPrice = driver.findElement(By.xpath(ProductPrice)).getText(); // Ürün seçilirken olan fiyat bilgisi eklenmiştir.
        driver.findElement(By.id(AddToBasketId)).click(); //Ürün sepete eklenir.

        driver.findElement(By.xpath(MyBasket)).click();

        String newPrice= driver.findElement(By.xpath(NewProductPrice)).getText(); //Sepete atıldıktan sonra karşılaşılan fiyat girilir.
        assertEquals(productPrice, newPrice); // Ürün ve Sepette olan ürün fiyat karşılaştırması yapılır.

        driver.findElement(By.xpath(NumberOfProduct)).click(); // Sepetteki ürün adedi arttırılır.
        driver.findElement(By.xpath(SelectedNumberOfProduct)).click();


        WebElement deleteButton = driver.findElement(By.cssSelector(DeleteProduct)); //Sepetteki ürün silinir.
        deleteButton.click();

        driver.quit();
    }
}
