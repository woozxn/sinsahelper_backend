package com.ujm.sinsahelper.service.Item;

import com.ujm.sinsahelper.domain.ItemDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CrawlingService {
    private final String DRIVER_ID = "webdriver.chrome.driver";
    //크롬 드라이버 경로 설정
    private final String DRIVER_PATH = "./src/main/resources/chromedriver.exe";

    public String crawlingReview(String itemUrl) {
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


            List<WebElement> element = driver.findElements(By.className("review-contents__text"));
            review = element.get(0).getText();
            System.out.println(review);



        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.close();
        return review;
    }

    public void crawlingItemInfo(ItemDto item, String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        Elements content1 = doc.select("div[class=product_info]");

        String photo = "";

        String[] cate = content1.get(0).text().split(" ");
        photo = doc.select("div[class=product-img]").select("img").attr("src");
        if(photo.substring(0,1).equals("/")){
            photo = "https:" + photo;
        }

        item.setItemName(doc.select("span[class=product_title]").get(0).text());

        item.setMainCategory(cate[0]);
        item.setSubCategory(cate[2]);

        item.setPhoto(photo);

    }

    public Long crawlingPrice(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        Long price;

        String priceString = doc.select("span[class=product_article_price]").get(0).text();
        priceString = priceString.substring(0,priceString.length()-1);
        priceString = priceString.replace(",","");

        price = Long.parseLong(priceString);


        return price;
    }
}
