package com.ujm.sinsahelper.service.Item;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrawlingService {
    private final String DRIVER_ID = "webdriver.chrome.driver";
    //크롬 드라이버 경로 설정
    private final String DRIVER_PATH = "./src/main/resources/chromedriver.exe";

    // KCH : System.setProperty 수정 해야함
//    public void CrawlingComment() {
//        System.setProperty(DRIVER_ID, DRIVER_PATH);
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
//        options.addArguments("--window-size=1400,600");
//        WebDriver driver = new ChromeDriver(options);
//
//
//        //url 설정
//        String base_url = "https://store.musinsa.com/app/goods/2411186";
//
//
//        try {
//            driver.get(base_url);
//            String[] en = driver.findElement(By.className("box_page_msg")).getText().split(" ");
//            int end = Integer.parseInt(en[0]);
////            System.out.println("end = " + end);
//
//            System.out.printf("*****page1*****");
//            List<WebElement> element = driver.findElements(By.className("review-contents__text"));
//            for (WebElement el : element) {
//                System.out.println(el.getText());
//            }
//            for (int i = 2; i <= end; i++) {
//                int page = (i % 5 > 1) ? (i % 5) + 2 : (i % 5) + 7;
//
////                WebElement NextPage = driver.findElement(By.xpath("//*[@id=\"reviewListFragment\"]/div[11]/div[2]/div/a[" + page +"]"));
////                NextPage.click();
//                WebDriverWait wait = new WebDriverWait(driver, 10);
//                WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"reviewListFragment\"]/div[11]/div[2]/div/a[" + page + "]")));
//
//                System.out.printf("*****page" + i + "*****");
//
//                element = driver.findElements(By.className("review-contents__text"));
//                for (WebElement el : element) {
//                    System.out.println(el.getText());
//                }
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public String CrawlingPrice(String itemUrl) {
        System.setProperty(DRIVER_ID, DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1400,600");
        WebDriver driver = new ChromeDriver(options);

        //url 설정
        String base_url = itemUrl;

        String en = null;
        try {
            driver.get(base_url);
            en = driver.findElement(By.className("product_article_price")).getText();
            en=en.substring(0,en.length()-1);
            en=en.replace(",","");
            System.out.println("en = " + en);

        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.close();
        return en;
    }

    public String CrawlingPhoto(String itemUrl) {
        // Chromedriver 환경 설정
        System.setProperty(DRIVER_ID, DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1400,600");
        WebDriver driver = new ChromeDriver(options);

        //url 설정
        String base_url = itemUrl;

        String en = null;
        try {
            driver.get(base_url);
            en = driver.findElement(By.xpath("//*[@id=\"bigimg\"]")).getAttribute("src");
            System.out.println("en = " + en);

        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.close();
        return en;
    }

    public String CrawlingReview(String itemUrl) {
        System.setProperty(DRIVER_ID, DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1400,600");
        WebDriver driver = new ChromeDriver(options);


        //url 설정
        String base_url = itemUrl;

        String review = "";

        try {
            driver.get(base_url);
            String[] en = driver.findElement(By.className("box_page_msg")).getText().split(" ");
            // review 최대 5개 넣기
            int end = 5 <= Integer.parseInt(en[0]) ? 5:Integer.parseInt(en[0]);

            List<WebElement> element = driver.findElements(By.className("review-contents__text"));
            review = element.get(0).getText();
            System.out.println(review);



        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.close();
        return review;
    }
}
