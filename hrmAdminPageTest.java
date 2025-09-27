
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.swing.*;
import java.time.Duration;

public class hrmAdminPageTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private String LOGIN_PAGE_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    private String USERNAME = "Admin";
    private String PASSWORD = "admin123";

    //    setup môi trường giả lập
    @BeforeTest
    public void setUp() throws InterruptedException{
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        driver.manage().window().maximize();
        driver.get(LOGIN_PAGE_URL);
        Thread.sleep(10000);


//        enter username
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys(USERNAME);
//        enter password
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys(PASSWORD);
//        click login button
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();
        Thread.sleep(5000);

        //        click admin button
        WebElement adminButton = driver.findElement(By.xpath("//span[text()='Admin']"));
        adminButton.click();
        Thread.sleep(3000);



    }

    //    locator 1, 2, 3: kiểm tra hiển thị các danh mục trong sidebar
    @Test(priority = 1)
    public void testSidebar() throws  InterruptedException{
        String[] menuItems = {"Admin", "PIM", "Leave"};
        for (String item: menuItems){
            WebElement menu = driver.findElement(By.xpath("//span[text()='" + item + "']"));
            Assert.assertTrue(menu.isDisplayed(),  "Menu item '" + item + "' is not displayed");
            actions.moveToElement(menu).perform();
            Thread.sleep(3000);
        }
    }

    @Test(priority = 2)
    public void testTopbarMenu() throws InterruptedException{
        //        WebElement userManagement = driver.findElement(By.xpath("//nav[@aria-label='Topbar Menu']//span[text()='User Management ']"));
        WebElement userManagement = driver.findElement(By.xpath("//span[text()='User Management ']"));
        Assert.assertTrue(userManagement.isDisplayed());
    }

    @Test(priority = 3)
    public void testSearchUser() throws InterruptedException{
//        trong trang Admin
//        locator 4: tìm textbox của Username và nhập từ khóa 'Admin'
        WebElement usernameTextbox = driver.findElement(By.xpath("//label[text()='Username']/../following-sibling::div//input"));
        usernameTextbox.sendKeys("Admin");
//        locator 5: tìm userRoleDropdown
        WebElement userRoleDropdown = driver.findElement(By.xpath("//label[text()='User Role']/../following-sibling::div//div[@class='oxd-select-wrapper']"));
        userRoleDropdown.click();
//        locator 6, 7: click chọn các item trong userRoleDropdown
//        option Admin: //div[@role='listbox']//span[text()='Admin']
//        option ESS: //div[@role='listbox']//span[text()='ESS']
        WebElement roleOption = driver.findElement(By.xpath("//div[@role='listbox']//span[text()='Admin']"));
        roleOption.click();
//        locator 10:
        WebElement searchButton = driver.findElement(By.xpath("//div[@class='oxd-form-actions']//button[@type='submit']"));
        searchButton.click();
        Thread.sleep(3000);

        //        locator 12:
        WebElement usernameCell = driver.findElement(By.xpath("//div[@class='oxd-table-card']//div[2]//div[text()='Admin']"));
        Assert.assertTrue(usernameCell.isDisplayed());
        actions.moveToElement(usernameCell).perform();

        // locator 13:
        WebElement userRoleCell = driver.findElement(By.xpath("//div[@class='oxd-table-card']//div[3]//div[text()='Admin']"));
        Assert.assertTrue(userRoleCell.isDisplayed());

//        locator 9:
        WebElement resetButton = driver.findElement(By.xpath("//div[@class='oxd-form-actions']//button[@type='button']"));
        resetButton.click();
        Thread.sleep(3000);
    }

    @Test(priority = 4)
    public void testEmployeeName() throws InterruptedException{
//        locator 8: Tìm textbox Employee Name và nhập từ khóa 'user', chọn Employee name
        WebElement employeeNameTextbox = driver.findElement(
                By.xpath("//label[text()='Employee Name']/../following-sibling::div//input[@placeholder='Type for hints...']"));
        Assert.assertTrue(employeeNameTextbox.isDisplayed(), "Employee Name textbox is not display");
//        WebElement employeeName = driver.findElement(By.xpath("//div[@role='listbox']//span[1]"));
//        actions.click(employeeNameTextbox).sendKeys("user").pause(Duration.ofSeconds(3)).click(employeeName).perform();
    }

    @Test(priority = 5)
    public void testActionButton() throws InterruptedException{
//        locator 14, 15:
//        //div[@class='oxd-table-cell-actions']//button[1]
        WebElement deleteButton = driver.findElement(By.xpath("//div[@class='oxd-table-cell-actions']//button[1]"));
        Assert.assertTrue(deleteButton.isDisplayed(), "Delete button is not displayed");

        WebElement updateButton = driver.findElement(By.xpath("//div[@class='oxd-table-cell-actions']//button[2]"));
        Assert.assertTrue(updateButton.isDisplayed(), "Update button is not displayed");

    }

    //    locator 16, 17: Kiểm tra hiển thị header của danh sách kết quả tìm kiếm
    @Test(priority = 6)
    public void testTableHeader() throws InterruptedException{
        String[] userTableHeader = {"Username", "User Role", "Employee Name", "Status", "Actions"};
        for(String item: userTableHeader){
            WebElement headerElement = driver.findElement(By.xpath("//div[@class='oxd-table-header']//div[text()='" + item + "']"));
            Assert.assertTrue(headerElement.isDisplayed(), "Header Element " + item + " is not displayed");
        }
    }

    //    locator 18: check display admin/user managent title
    @Test(priority = 7)
    public void testAdminTitle() throws InterruptedException{
        WebElement adminTitle = driver.findElement(By.xpath("//h6[text()='Admin']"));
        Assert.assertTrue(adminTitle.isDisplayed());

        WebElement userManagementTitle = driver.findElement(By.xpath("//h6[text()='User Management']"));
        Assert.assertTrue(userManagementTitle.isDisplayed());

    }

    //    locator 19: Main Menu Button
    @Test(priority = 8)
    public void testMainMenuButton() throws InterruptedException{
        WebElement mainMenuButton = driver.findElement(By.xpath("//button[contains(@class,'oxd-main-menu-button')]"));
        mainMenuButton.click();
        Thread.sleep(2000);
        mainMenuButton.click();
        Thread.sleep(2000);
    }


    //    locator 20: kiểm tra
    @Test(priority = 9)
    public void testToggleSearchButton() throws InterruptedException {
        WebElement userForm = driver.findElement(By.xpath("//div[@class='oxd-table-filter-area']"));
        WebElement toggleSearchButton = driver.findElement(By.xpath("//button//i[contains(@class, 'bi-caret-up-fill')]"));
        toggleSearchButton.click();
        Assert.assertFalse(userForm.isDisplayed(), "Userform should be hidden");
        toggleSearchButton.click();
        Thread.sleep(3000);
        Assert.assertTrue(userForm.isDisplayed(), "Userform is not displayed");
    }

    //    locator 21:
    @Test(priority = 10)
    public void testAddButton() throws InterruptedException{
        WebElement addButton = driver.findElement(By.xpath("//div[@class='orangehrm-header-container']//button"));
        addButton.click();
        Thread.sleep(3000);

        WebElement cancelButton = driver.findElement(By.xpath("//button[text()=' Cancel ']"));
        cancelButton.click();
        Thread.sleep(3000);

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/admin/viewSystemUsers"));
        Thread.sleep(3000);
    }


    @AfterTest
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }
}
