package demo;

import org.apache.xmlbeans.impl.common.SystemCache;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
        ChromeDriver driver;
        WebDriverWait wait;
        SoftAssert softAssert;
        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();

        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }

        //testCase01: Go to YouTube.com and Assert you are on the correct URL. 
        //Click on "About" at the bottom of the sidebar, and print the message on the screen.
        
        @Test(enabled = true)
        public void testCase01() throws InterruptedException{
                System.out.println("start of testCase01");
                wait=new WebDriverWait(driver, Duration.ofSeconds(5l));
                Boolean status=false;
                driver.get("https://www.youtube.com/");
                Thread.sleep(5000);
                //verifying the current page url
                status = Wrappers.verifyCurrentPageURL(driver,"youtube");
                Assert.assertTrue(status,"failed to verify currentUrl");
                Wrappers.clickOnLeftBarElement(driver, By.xpath("//div[@id='guide-links-primary']/a[contains(text(),'About')]"), By.xpath("//div[@id='start']//yt-icon-button[@id='guide-button']"));
                status = Wrappers.verifyCurrentPageURL(driver,"about");
                //verify abouut text on about page
                status = Wrappers.verifyTextMsg(driver, By.xpath("//section[@class='ytabout__content']/h1"), "About");
                //extract about page message
                Wrappers.extractMessage(driver);
                System.out.println("End of testCase01");
        }

        //testCase02: Go to the "Films" or "Movies" tab and in the “Top Selling” section,
        //scroll to the extreme right. Apply a Soft Assert on whether the movie is marked “A” 
        //for Mature or not. Apply a Soft assert on the movie category to check if it exists
        // ex: "Comedy", "Animation", "Drama".

        @Test(enabled = true)
        public void testCase02() throws InterruptedException{
                System.out.println("start of testCase02");
                Boolean status=false;
                driver.get("https://www.youtube.com/");
                Thread.sleep(3000);
                Wrappers.clickOnLeftBarElement(driver, By.xpath("//a[@id='endpoint']//yt-formatted-string[contains(text(),'Movies') or contains(text(),'Films')]"), By.xpath("//div[@id='start']//yt-icon-button[@id='guide-button']"));
                Wrappers.waitCondition(driver, By.xpath("//span[contains(text(),'Top selling')]"));
                Wrappers.clickOnRightArrow(driver, By.xpath("//span[contains(text(),'Top selling')]//ancestor::div[@id='dismissible']//descendant::button[@aria-label='Next']"));
                Thread.sleep(4000);
                // String ageContent = Wrappers.verifyTheAgeCategory(driver,By.xpath("//*[@id='items']/ytd-grid-movie-renderer[16]/ytd-badge-supported-renderer/div[2]/p"));
                // System.out.println("ageContent " + ageContent);
                 softAssert=new SoftAssert();
                // softAssert.assertEquals(ageContent, "A", "Movie is not rated A");
                // String movieCategory = Wrappers.verifyTheMovieCategory(driver, By.xpath("//*[@id='items']/ytd-grid-movie-renderer[16]/a/span"));
                // softAssert.assertTrue(movieCategory.equalsIgnoreCase("Comedy")
                //                         || movieCategory.equalsIgnoreCase("Animation")
                //                         || movieCategory.equalsIgnoreCase("Drama")
                //                         ,"Movie category is not as expected");
                Boolean ageContent = Wrappers.verifyTheAgeCategory(driver,By.xpath("//*[@id='items']/ytd-grid-movie-renderer[16]/ytd-badge-supported-renderer/div[2]/p"));
                softAssert.assertFalse(ageContent,"not for matured audience");
                Boolean movieCategory = Wrappers.verifyTheMovieCategory(driver, By.xpath("//*[@id='items']/ytd-grid-movie-renderer[16]/a/span"));
                softAssert.assertTrue(movieCategory,"movie category is not mentioned");
                softAssert.assertAll();
                System.out.println("End of testCase02");
                
        }

        //testCase03: Go to the "Music" tab and in the 1st section, scroll to the extreme right.
        // Print the name of the playlist. 
        //Soft Assert on whether the number of tracks listed is less than or equal to 50.

        @Test(enabled = true)
        public void testCase03() throws InterruptedException{
                System.out.println("start of testCase03");
             //   driver.manage().timeouts().implicitlyWait(20);
                Boolean status=false;
                driver.get("https://www.youtube.com/");
                Thread.sleep(3000);
                Wrappers.clickOnLeftBarElement(driver, By.xpath("//a[@id='endpoint']//yt-formatted-string[text()='Music']"), By.xpath("//div[@id='start']//yt-icon-button[@id='guide-button']"));
                Wrappers.waitCondition(driver, By.xpath("//span[contains(text(),'Discover New Music')]"));
                Wrappers.clickOnRightArrow(driver, By.xpath("//span[contains(text(),\"India's Biggest Hits\")]//ancestor::div[@id='dismissible']//div[@id='right-arrow']//button"));
                Wrappers.waitCondition(driver, By.xpath("//*[@id='items']/ytd-compact-station-renderer[11]/div/a/h3"));
                Wrappers.printNameOfPlaylist(driver, By.xpath("//*[@id='items']/ytd-compact-station-renderer[11]/div/a/h3"));
                status = Wrappers.validateNumberOfTrack(driver, By.xpath("//*[@id='items']/ytd-compact-station-renderer[11]/div/a/p"));
                softAssert = new SoftAssert();
                softAssert.assertTrue(status,"the tracks are less than 50");
                System.out.println("End of testCase03");
        }

        //testCase04: Go to the "News" tab and print the title and body of 
        //the 1st 3 “Latest News Posts” along with the sum of the 
        //number of likes on all 3 of them. No likes given means 0.

        @Test(enabled = true)
        public void testCase04() throws InterruptedException{
                System.out.println("start of testCase04");
                Boolean status=false;
                driver.get("https://www.youtube.com/");
                Thread.sleep(3000);
                Wrappers.clickOnLeftBarElement(driver, By.xpath("//a[@id='endpoint']//yt-formatted-string[text()='News']"), By.xpath("//div[@id='start']//yt-icon-button[@id='guide-button']"));
                Wrappers.waitCondition(driver, By.xpath("//span[contains(text(),'Latest news posts')]"));
                Wrappers.printTitleandContentOfTopNews(driver,By.xpath("//span[contains(text(),'Latest news posts')]/ancestor::div[@id='dismissible']/div[@id='contents']/ytd-rich-item-renderer"));
                Thread.sleep(4000);
        }

        //testCase05: Search for each of the items given in the 
        //stubs: src/test/resources/data.xlsx, and keep scrolling till 
        //the sum of each video’s views reach 10 Cr.

        @Test(dataProvider = "excelData", enabled = true)
        public void testCase05(String searchWord) throws InterruptedException{
            System.out.println("Start of testCase05");
            System.out.println("Running testCase05 for data : "+searchWord);
            driver.get("https://www.youtube.com/");
                Thread.sleep(3000);
                Wrappers.searchThroughSearchTextBox(driver, searchWord);
                Thread.sleep(4000);
                Wrappers.countNumberOfViews(driver);
                System.out.println("End of testCase05");
        }

}