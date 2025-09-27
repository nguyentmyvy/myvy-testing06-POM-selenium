import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class amazonSearchResultPageTest {
    private WebDriver driver;
    private String AMAZON_URL = "https://www.amazon.com/";

    @BeforeTest
    public void setUp() throws InterruptedException{
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

//        B1: Truy cập page amazon
        driver.get(AMAZON_URL);
        Thread.sleep(3000);

        WebElement btnContinue = driver.findElement(By.xpath("//button[text()='Continue shopping']"));
        btnContinue.click();
        Thread.sleep(3000);

        //        click location
//        Kiểm tra xem location phải ở Việt Nam không
        WebElement updateLocation = driver.findElement(By.id("glow-ingress-line2"));
        String actualLocation = updateLocation.getText().trim();
        String expectedLocation = "Vietnam";
        if(!actualLocation.equals(expectedLocation)) {
            updateLocation.click();
            Thread.sleep(3000);
            WebElement locationPopup = driver.findElement(By.xpath("//span[@class='a-dropdown-prompt']"));
            locationPopup.click();
            Thread.sleep(3000);
            WebElement locationVietNam = driver.findElement(By.xpath("//a[text()='Vietnam']"));
            locationVietNam.click();
            Thread.sleep(3000);
            WebElement doneButton = driver.findElement(By.xpath("//button[@name='glowDoneButton']"));
            doneButton.click();
            Thread.sleep(3000);
        }
//      element 4
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys("adidas");

//        cách 1 enter từ bàn phím
        searchBox.submit();
        Thread.sleep(3000);

//        cách 2: click search button
//        WebElement searchButton = driver.findElement(By.xpath("//input[@type='submit']"));
//        searchButton.click();
//        Thread.sleep(10000);

    }

    //    element 13: //h1[@data-csa-c-content-id='desktop/1/0/default/default']//h2/span[1]
    @Test(priority = 1)
    public void testTextResultSummary() throws InterruptedException{
        WebElement textResult = driver.findElement(By.xpath("//h1[@data-csa-c-content-id='desktop/1/0/default/default']//h2/span[1]"));
        Assert.assertTrue(textResult.isDisplayed());
        Thread.sleep(3000);
    }
    //    element 5: //div[@id='nav-cart-count-container']
    @Test(priority = 2)
    public void testCartButton() throws InterruptedException{
        WebElement cartButton = driver.findElement(By.xpath("//div[@id='nav-cart-count-container']"));
        Assert.assertTrue(cartButton.isDisplayed());
        Thread.sleep(3000);
    }

    //    element 6: //a[text()="Women's Footwear"]/ancestor::div[contains(@class,'s-visual-card-navigation-product-image-container')]
    @Test(priority = 3)
    public void testCategoryCard() throws InterruptedException{
        WebElement womenCategoryCard = driver.findElement(By.xpath("//a[text()=\"Women's Footwear\"]/ancestor::div[contains(@class,'s-visual-card-navigation-product-image-container')]"));
        Assert.assertTrue(womenCategoryCard.isDisplayed());
        Thread.sleep(3000);
    }

    //    element 11: id: loom-desktop-top-slot_us-slds-sp-2-t1-a2-heading
//    element 14: //div[contains(@class,'s-visual-card-navigation-product-image-container')]//a[contains(text(),'Bags')]
    @Test(priority = 4)
    public void testCollectionsCards() throws InterruptedException{
        WebElement collectionsTitle = driver.findElement(By.id("loom-desktop-top-slot_us-slds-sp-2-t1-a2-heading"));
        Assert.assertTrue(collectionsTitle.isDisplayed());
        WebElement bagsText = driver.findElement(By.xpath("//div[contains(@class,'s-visual-card-navigation-product-image-container')]//a[contains(text(),'Bags')]"));
        Assert.assertTrue(bagsText.isDisplayed());
        Thread.sleep(3000);
    }


    //    element 1: thẻ đầu tiên trong phần Result
//              img: //div[@data-index='4']//img
//              bestSellerLabel: id = B0CKMK7YR7-best-seller
//    element 2: //div[@data-index='8']//div[@class='a-section aok-inline-block']
//    element 3: //div[@data-index='8']//span[contains(@class,'a-price') and text()='$']
//    element 8: //h2[contains(@class, 'a-size-base-plus a-spacing-none a-color-base a-text-normal')]//span[contains(text(), 'Unisex-Adult Samba Indoor')]
//    element 9: //div[@data-index='5']//div[@data-cy='delivery-recipe']
//    element 10: //div[@data-cel-widget="search_result_4"]//h2//span[contains(text(),'adidas')]
    @Test(priority = 5)
    public void testResultCards() throws InterruptedException{
        WebElement productImage = driver.findElement(By.xpath("//div[@data-index='4']//img"));
        Assert.assertTrue(productImage.isDisplayed());

//        WebElement bestSellerLabel = driver.findElement(By.id("B0CKMK7YR7-best-seller"));
//        Assert.assertTrue(bestSellerLabel.isDisplayed());

        WebElement listPrice = driver.findElement(By.xpath("//div[@data-index='8']//div[@class='a-section aok-inline-block']"));
        Assert.assertTrue(listPrice.isDisplayed());

        WebElement productPrice = driver.findElement(By.xpath("//div[@data-index='8']//span[contains(@class,'a-price') and text()='$']"));
        Assert.assertTrue(productPrice.isDisplayed());

        WebElement productName = driver.findElement(By.xpath("//h2[contains(@class, 'a-size-base-plus a-spacing-none a-color-base a-text-normal')]//span[contains(text(), 'Unisex-Adult Samba Indoor')]"));
        Assert.assertTrue(productName.isDisplayed());

        WebElement deliveryDate = driver.findElement(By.xpath("//div[@data-index='5']//div[@data-cy='delivery-recipe']"));
        Assert.assertTrue(deliveryDate.isDisplayed());

        WebElement brandName = driver.findElement(By.xpath("//div[@data-cel-widget='search_result_4']//h2//span[text()='adidas']"));
        Assert.assertTrue(brandName.isDisplayed());
        Thread.sleep(3000);
    }

    //      element 12: men button
    @Test(priority = 6)
    public void testMenOption() throws InterruptedException{
        WebElement menOption = driver.findElement(By.xpath("//span[text()='Men']/preceding-sibling::div"));
        menOption.click();
        Thread.sleep(5000);
    }

    @AfterTest
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }
}


