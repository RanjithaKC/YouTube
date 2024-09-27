package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

     public static void launchURL(WebDriver driver, String url){
        driver.get(url);
     }

     public static Boolean verifyCurrentPageURL(WebDriver driver, String expectedValue){
        //verifying the URL
        Boolean status=false;
        String currentURL = driver.getCurrentUrl();
        if(currentURL.contains(expectedValue)){
            status=true;
        }
        return status;
     }

     public static Boolean clickOnLeftBarElement(WebDriver driver, By locator, By menueLocator){
        Boolean status = false;
        try{
        WebElement elementToClick = driver.findElement(locator);
        if(!elementToClick.isDisplayed()){
            WebElement menueElement = driver.findElement(menueLocator);
            menueElement.click();
        }
        elementToClick.click();
        status = true;
    }catch(Exception e ){
        e.printStackTrace();
    }
        return status;
     }

     public static void scrollDown(WebDriver driver){
        JavascriptExecutor js = (JavascriptExecutor)driver;
      //  js.executeScript("arguments[0].window.scrollIntoView(true);",elementToScroll);
      js.executeScript("window.scrollBy(0,1000);");
     }

     public static void extractMessage(WebDriver driver){
        try{
        WebElement locator1 = driver.findElement(By.xpath("//section[@class='ytabout__content']/h1"));
        WebElement locator2 = driver.findElement(By.xpath("//section[@class='ytabout__content']/p[1]"));
        WebElement locator3 = driver.findElement(By.xpath("//section[@class='ytabout__content']/p[2]"));

        String concatMsg = locator1.getText()+" "+locator2.getText()+" "+locator3.getText();
        System.out.println("Message on about page : "+concatMsg);
        }catch(Exception e){
            e.printStackTrace();
        }

     }

     public static void waitCondition(WebDriver driver, By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5l));
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
     }

     public static Boolean verifyTextMsg(WebDriver driver, By locator, String expectedValue){
        Boolean status = false;
        try{
        WebElement TxtMsgElement = driver.findElement(locator);
        if(TxtMsgElement.getText().contains(expectedValue)){
            status = true;
        }
    }catch(Exception e){
        e.printStackTrace();
    }
        return status;
     }
     public static void clickOnRightArrow(WebDriver driver, By locator){
        try{
        WebElement rightArrowButton = driver.findElement(locator);
        while(rightArrowButton.isDisplayed()){
            rightArrowButton.click();
           // System.out.println("clicking on right arrow");
        }
    }catch(Exception e){
        e.printStackTrace();
    }
        
     }

     public static Boolean verifyTheAgeCategory(WebDriver driver, By locator){
        Boolean status = false;
        try{
        WebElement ageElement = driver.findElement(locator);
       // return ageElement.getText();
       if(ageElement.getText().contains("A")){
        status = true;
       }
    }catch(Exception e){
        e.printStackTrace();
    }
       return status;
     }

     public static Boolean verifyTheMovieCategory(WebDriver driver, By locator){
        Boolean status = false;
        try{
        WebElement movieCategory = driver.findElement(locator);
        String movieGenere = movieCategory.getText().replaceAll("[^a-zA-Z]", "").trim();
        System.out.println("movieGenere "+movieGenere);
        if(movieGenere.contains("Comedy")|| movieGenere.contains("Animation")|| movieGenere.contains("Drama")){
            status= true;
        }
    }catch(Exception e){
        e.printStackTrace();
    }
        return status;
       // return movieGenere;
     }

     public static void printNameOfPlaylist(WebDriver driver, By locator){
        WebElement playlist = driver.findElement(locator);
        String playlistName = playlist.getText();
        System.out.println("Playlist title is "+ playlistName);
     } 

     public static Boolean validateNumberOfTrack(WebDriver driver, By locator){
        Boolean status = false;
        WebElement trackElement = driver.findElement(locator);
        String track = trackElement.getText().replaceAll("[^\\d]", "").trim();
        int numberOfTrack = Integer.parseInt(track);
        System.out.println("number of tracks : "+numberOfTrack);
        if(numberOfTrack>=50){
            status = true;
        }
        return status;
     }

     public static void printTitleandContentOfTopNews(WebDriver driver, By locator){
        List<WebElement> parancards=driver.findElements(locator);
        long totalLikes = 0l;
        for(int i=0; i<3; i++){
            String header = parancards.get(i).findElement(By.xpath(".//div[@id='header']")).getText();
            System.out.println("Title of the news : "+ header);
            String description = parancards.get(i).findElement(By.xpath(".//div[@id='body']/div[@id='post-text']")).getText();
            System.out.println("Description of news : "+description);
            String likes = parancards.get(i).findElement(By.xpath(".//span[@id='vote-count-middle']")).getText();
            System.out.println("number of likes : "+likes);
            totalLikes+=Wrappers.convertToNumericValue(likes);
            System.out.println("total number of likes : "+totalLikes);

        }
     }

     public static long convertToNumericValue(String value){
        value=value.trim().toUpperCase();
        char lastChar = value.charAt(value.length()-1);
        int multiplier = 1;
        switch(lastChar){
            case 'K':
            multiplier = 1000;
            break;
            case 'M':
            multiplier = 1000000;
            break;
            case 'B':
            multiplier = 1000000000;
            break;
            default:
            if(Character.isDigit(lastChar)){
                return Long.parseLong(value);
            }
            throw new IllegalArgumentException("invalid format : "+value);
        }
            String numericPart = value.substring(0, value.length()-1);
            double number = Double.parseDouble(numericPart);
            return (long)(number*multiplier);
    }

    public static void searchThroughSearchTextBox(WebDriver driver, String searchWord){
        WebElement searchBox = driver.findElement(By.xpath("//input[@id='search']"));
        searchBox.clear();
        searchBox.sendKeys(searchWord);
        searchBox.submit();
    }

    public static void countNumberOfViews(WebDriver driver) throws InterruptedException{
        long totalViews = 0l;
        try{
        while(totalViews<100000000L){
            List<WebElement> views = driver.findElements(By.xpath("//div[@id='title-wrapper']/ancestor::div[@class='text-wrapper style-scope ytd-video-renderer']//span[contains(text(),'views')]"));
            for(WebElement view : views){
                String viewText = view.getText().replaceAll("views", "").trim();
                System.out.println(" view text : "+viewText);
                long viewsCount = convertToNumericValue(viewText);
                System.out.println("views count : "+viewsCount);
                totalViews += viewsCount;
                System.out.println("totalViews : "+totalViews);
                if (totalViews >= 100000000L) {
                    break;
                }
            }
            Wrappers.scrollDown(driver);
            Thread.sleep(3000);
        }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

