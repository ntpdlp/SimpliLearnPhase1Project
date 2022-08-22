package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TestAmazonIn {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();


        try {

            driver.get("https://www.amazon.in/");
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);

            //Explicit wait
            WebDriverWait wait = new WebDriverWait(driver,30);
            WebElement SearchInput = driver.findElement(By.xpath("//input[contains(@name,'field-keywords')]"));
            SearchInput.click();
            SearchInput.sendKeys("Samsung");
            SearchInput.sendKeys(Keys.ENTER);

            //search Samsung list : print header , price
            Thread.sleep(5000);
            WebElement resultlist = driver.findElement(By.xpath("//span[@data-component-type='s-search-results']"));
            List<WebElement> resultList = driver.findElements(By.xpath("//span[@data-component-type='s-search-results']//div[@data-component-type='s-search-result']"));
            List<WebElement> headers = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']//h2"));
            List<WebElement> prices = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']//span[@class='a-price']"));

            for (int i=0; i<resultList.size(); i++) {
                System.out.println(headers.get(i).getText());
                System.out.println(prices.get(i).getText());
            }


            //click 1st product
            String parentWindowHandle = driver.getWindowHandle();
            String firstItemTitle="";
            List<WebElement> linkProducts = driver.findElements(By.cssSelector("div .s-result-list.s-main-slot h2 a"));
            System.out.println(linkProducts.get(0).getAttribute("href"));
            firstItemTitle=linkProducts.get(0).getText();
            System.out.println(firstItemTitle);
            linkProducts.get(0).click();

            //handle for multiple tabs , switch to current acting window
            Set<String> allWins = driver.getWindowHandles();
            for (String win : allWins) {
                if(!win.equals(parentWindowHandle)){
                    driver.switchTo().window(win);
                }
            }

//            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#titleSection")));
            String headerProductOnDetailPage = driver.findElement(By.cssSelector("#titleSection")).getText();
            System.out.println(headerProductOnDetailPage);
            if(headerProductOnDetailPage.compareTo(firstItemTitle)!=0){
                Assert.fail();
            }else Assert.assertTrue(1==1);


            //Done
            Thread.sleep(3000);


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            driver.quit();
        }

    }
}
