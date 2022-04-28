package com.ujm.sinsahelper.service.Item;

//package springstudy.functiontest.crawling.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CrawlingTest {
    public static void main(String[] args) {

        String DRIVER_ID = "webdriver.chrome.driver";
        //크롬 드라이버 경로 설정
        String DRIVER_PATH = "./src/main/resources/chromedriver.exe";

        System.setProperty(DRIVER_ID,DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1400,600");
        WebDriver driver = new ChromeDriver(options);


        //url 설정
        String base_url ="https://store.musinsa.com/app/goods/1557508";



        String en = null;
        try {
            driver.get(base_url);
            WebElement img = driver.findElement(By.xpath("//*[@id=\"bigimg\"]"));
            System.out.println("img = " + img.toString());
            en = img.getAttribute("src");
            System.out.println("en = " + en);

        } catch (Exception e) {
            e.printStackTrace();
        }


//        try{
//            driver.get(base_url);
//            String[] en = driver.findElement(By.className("box_page_msg")).getText().split(" ");
//            int end = Integer.parseInt(en[0]);
//            System.out.println("end = " + end);
//
//            System.out.printf("*****page1*****");
//            List<WebElement> element = driver.findElements(By.className("review-contents__text"));
//            for (WebElement el : element){
//                System.out.println(el.getText());
//            }
//            for(int i = 2; i <= end; i++){
//                int page = (i%5 > 1)? (i%5) + 2: (i%5) + 7;
//
////                WebElement NextPage = driver.findElement(By.xpath("//*[@id=\"reviewListFragment\"]/div[11]/div[2]/div/a[" + page +"]"));
////                NextPage.click();
//                WebDriverWait wait = new WebDriverWait(driver, 10);
//                WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"reviewListFragment\"]/div[11]/div[2]/div/a[" + page +"]")));
//
//                System.out.printf("*****page" + i + "*****");
//
//                element = driver.findElements(By.className("review-contents__text"));
//                for (WebElement el : element){
//                    System.out.println(el.getText());
//                }
//            }
//
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
}
