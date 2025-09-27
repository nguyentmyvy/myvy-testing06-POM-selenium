
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class airbnbHomePageTest {
    private WebDriver driver;
    private String HOME_URL = "https://demo4.cybersoft.edu.vn/";
    private JavascriptExecutor js;

    @BeforeTest
    public void setUp() throws InterruptedException{
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        driver.manage().window().maximize();

        driver.get(HOME_URL);
        Thread.sleep(4000);

    }

    @Test(priority = 1)
    public void testHeader() throws InterruptedException{
//        locator 1: logo va ten logo
        WebElement logoName = driver.findElement(By.xpath("//span[text()='airbnb']"));
        Assert.assertTrue(logoName.isDisplayed(), "Logo Name khong hien thi");
        WebElement logo = driver.findElement(By.xpath("//img[@class='h-8']"));
        Assert.assertTrue(logo.isDisplayed(), "Logo not displayed");

//        locator 2,3
        String[] menuItems = {"Home", "About", "Services", "Pricing", "Contact"};
        for(String item: menuItems){
            WebElement homeMenu = driver.findElement(By.xpath("//ul[contains(@class, 'menu-phone')]//a[text()='" + item +"']"));
            Assert.assertTrue(homeMenu.isDisplayed(), "Menu items "+ item +" khong hien thi");
        }
//      locator 4:
        WebElement avatarMenu = driver.findElement(By.xpath("//img[@src='https://cdn-icons-png.flaticon.com/512/6596/6596121.png']"));
        Assert.assertTrue(avatarMenu.isDisplayed());
        Thread.sleep(3000);

    }

    //    Locator 5-9
    @Test(priority = 2)
    public void testSearchBar() throws InterruptedException{
//        locator 5: kiểm tra hiển thị địa điểm
        WebElement locationLabel = driver.findElement(By.xpath("//div[contains(@class,'col-span-3')]//p[text()='Địa điểm']"));
        Assert.assertTrue(locationLabel.isDisplayed());

        WebElement locationPlaceHolder = driver.findElement(By.xpath("//div[contains(@class,'col-span-3')]//p[text()='Bạn sắp đi đâu?']"));
        Assert.assertTrue(locationPlaceHolder.isDisplayed());

//        locator 6: kiểm tra hiển thị dd/mm/yyyy - dd/mm/yyyy
        WebElement dateLabel = driver.findElement(By.xpath("//div[contains(@class,'col-span-4')]"));
        Assert.assertTrue(dateLabel.isDisplayed());

//        locator 7: label thêm khách
        WebElement addGuestLabel = driver.findElement(By.xpath("//div[contains(@class,'col-span-3')]//p[text()='Thêm khách']"));
        Assert.assertTrue(addGuestLabel.isDisplayed());
        addGuestLabel.click();
        Thread.sleep(3000);

//        locator 8, 9: button
        WebElement increaseButton = driver.findElement(By.xpath("//div[text()='Khách']/following-sibling::div/button[.='+']"));
        increaseButton.click();
        Thread.sleep(3000);

        WebElement decreaseButton = driver.findElement(By.xpath("//div[text()='Khách']/following-sibling::div/button[.='-']"));
        decreaseButton.click();
        Thread.sleep(3000);

//        Search button
        WebElement searchButton = driver.findElement(By.xpath("//span[@aria-label='search']"));
        searchButton.click();
        Thread.sleep(3000);

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/rooms"), "Hiển thị trang room thấy bại");
        Thread.sleep(3000);
        WebElement logoName = driver.findElement(By.xpath("//span[text()='airbnb']"));
        logoName.click();
        Thread.sleep(3000);
    }

    @Test(priority = 3)
    public void testFilterButton() throws InterruptedException{
        //        locator 12: button [Loại nơi ở]
        WebElement accommodationButton = driver.findElement(By.xpath("//button[text()='Loại nơi ở']"));
        Assert.assertTrue(accommodationButton.isDisplayed());

//        locator 13: button [Giá]
        WebElement priceButton = driver.findElement(By.xpath("//button[text()='Giá']"));
        Assert.assertTrue(priceButton.isDisplayed());
        Thread.sleep(3000);

    }

    //    locator 10, 11, 14
    @Test(priority = 4)
    public void testCard() throws InterruptedException{
//        locator 10: hien thi card địa điểm Hồ Chí Minh
        WebElement hoChiMinhLocationCard = driver.findElement(By.xpath("//a[@href='/rooms/ho-chi-minh' and .//h2[text()='Hồ Chí Minh']]"));
        Assert.assertTrue(hoChiMinhLocationCard.isDisplayed());

//        locator 11: kiểm tra hiển thị tên địa điểm của card
        WebElement nameTag = driver.findElement(By.xpath("//a[@href='/rooms/can-tho']//h2"));
        Assert.assertTrue(nameTag.isDisplayed());
//        Thread.sleep(3000);

        WebElement timedrive = driver.findElement(By.xpath("//h2[text()='Nha Trang']/following-sibling::p"));
        Assert.assertTrue(timedrive.isDisplayed());


//cần thêm code scroll xuống vị trí card Can Tho mới thực hiện click được
        js.executeScript("arguments[0].scrollIntoView(true);",nameTag);
        js.executeScript("arguments[0].click();", nameTag);
        Thread.sleep(3000);

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/can-tho"), "URL hiện tại không chứa '/can-tho' như mong đợi");
        Thread.sleep(3000);

    }

    @AfterTest
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }
}
