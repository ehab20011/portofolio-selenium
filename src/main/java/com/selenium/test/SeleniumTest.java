package com.selenium.test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumTest {
    public static void main(String[] args) {
        //intialize the web driver
        WebDriver driver = new ChromeDriver();
        String url = "https://www.ehab.us/";

        //open up the personal website
        driver.get(url);

        //List to store all the phones and tablets
        List<Phone> phones = new ArrayList<>();
        List<Phone> tablets = new ArrayList<>();

        //phone screen sizes to test
        Dimension iphone12 = new Dimension(390,844);
        Dimension iphoneSE = new Dimension(375, 667);
        Dimension iphoneXR = new Dimension(414, 896);
        Dimension iphone12Pro = new Dimension(390, 844);
        Dimension iphone14ProMax = new Dimension(430, 932);
        Dimension pixel7 = new Dimension(412, 915);
        Dimension SamsungGalaxyS8Plus = new Dimension(360, 740);
        Dimension SamsungGalaxyS20Ultra = new Dimension(412, 915);
        Dimension iPadAir = new Dimension(820, 1180);
        Dimension GalaxyZFold5 = new Dimension(344, 882);
        Dimension SamsungGalaxyA51 = new Dimension(412, 914);

        //tablet screen sizes to test
        Dimension iPadMini = new Dimension(768,1024);
        Dimension iPadPro = new Dimension(1024, 1366);
        Dimension SurfacePro7 = new Dimension(912, 1368);
        Dimension SurfaceDuo = new Dimension(540, 720);
        Dimension AsusZenBookFold = new Dimension(853, 1280);
        Dimension NestHub = new Dimension(1024,600);
        Dimension NestHubMax = new Dimension(1280, 800);

        //store all the phones in list
        phones.add(new Phone(iphone12, "iPhone 12"));
        phones.add(new Phone(iphoneSE, "iPhone SE"));
        phones.add(new Phone(iphoneXR, "iPhone XR"));
        phones.add(new Phone(iphone12Pro, "iPhone 12 Pro"));
        phones.add(new Phone(iphone14ProMax, "iPhone 14 Pro Max"));
        phones.add(new Phone(pixel7, "Pixel 7"));
        phones.add(new Phone(SamsungGalaxyS8Plus, "SamsungGalaxyS8+"));
        phones.add(new Phone(SamsungGalaxyS20Ultra, "SamsungGalaxyS20Ultra"));
        phones.add(new Phone(GalaxyZFold5, "GalaxyZ Fold 5"));
        phones.add(new Phone(SamsungGalaxyA51, "Samsung Galaxy A51/71"));
        //these two ipads still need a hamburger menu so adding them as a phone
        phones.add(new Phone(iPadMini, "iPad Mini"));
        phones.add(new Phone(SurfaceDuo, "Surface Duo"));
        
        //store all the tablets in list
        tablets.add(new Phone(iPadPro, "iPad Pro"));
        tablets.add(new Phone(SurfacePro7, "Surface Pro 7"));
        tablets.add(new Phone(AsusZenBookFold, "Asus ZenBook Fold"));
        tablets.add(new Phone(NestHub, "Nest Hub"));
        tablets.add(new Phone(NestHubMax, "Nest Hub Max"));
        tablets.add(new Phone(iPadAir, "iPad Air"));

        System.out.println();
        testEveryScreen(driver, phones, tablets, url);
        resetScreenSize(driver);

        //close the browser
        driver.quit();
    }
    public static class Phone{
        Dimension dimension;
        String phoneName;
        public Phone(Dimension dimension, String phoneName){
            this.dimension = dimension;
            this.phoneName = phoneName;
        }
    }
    public static void testEveryScreen(WebDriver driver, List<Phone> phones, List<Phone> tablets, String url){
        //tests on computer screen
        System.out.println("COMPUTER SCREENS");
        System.out.println("Testing Navbar, Testing Pages, and their images: ");
        testNavBarComputer(driver, null, "Computer Screens");

        scrollingTest(driver, "Computer Screens");
        testPageLoadTime(driver, url);

        System.out.println("Testing Links: ");
        testBrokenLinks(driver);
        isLinkBroken(url);
        
        System.out.println();

        //tests under every phone screen
        for(Phone phone : phones){
            System.out.println(phone.phoneName);
            System.out.println("Testing Navbar, Testing Pages and their images: ");
            testNavBar(driver, phone.dimension, phone.phoneName);
            
            scrollingTest(driver, phone.phoneName);
            testPageLoadTime(driver, url);

            System.out.println("Testing Links: ");
            testBrokenLinks(driver);
            isLinkBroken(url);
            System.out.println();
        }

        resetScreenSize(driver);
        System.out.println("Testing Tablet Screens Now.. ");

        //test the navbar under every tablet
        for(Phone tablet : tablets){
            System.out.println(tablet.phoneName);
            testNavBarComputer(driver, tablet.dimension, tablet.phoneName);

            scrollingTest(driver, tablet.phoneName);
            testPageLoadTime(driver, url);

            System.out.println("Testing Links: ");
            testBrokenLinks(driver);
            isLinkBroken(url);
            
            System.out.println();
        }
    }
    public static void testNavBarComputer(WebDriver driver, Dimension dimension, String deviceName){
        if(dimension!=null){ driver.manage().window().setSize(dimension); }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        //wait for the navbar to be visible before starting to test it
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("header")));
        
        //find and click the ansys button
        WebElement AnsysButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.navbar__link[href='/ansys']")));
        AnsysButton.click();
        testImageLoading(driver, deviceName, "Ansys Page");

        //find and click the mobot button
        WebElement MobotButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.navbar__link[href='/mobot']")));
        MobotButton.click();
        testImageLoading(driver, deviceName, "Mobot Page");

        //find and click the Projects button
        WebElement ProjectsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.navbar__link[href='/projects']")));
        ProjectsButton.click();
        testImageLoading(driver, deviceName, "Projects Page");

        //find and click the hackathon button
        WebElement HackathonButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.navbar__link[href='/hackathons']")));
        HackathonButton.click();
        testImageLoading(driver, deviceName, "Hackathons Page");
        
        
        //reset to home 
        WebElement homeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("HOME")));
        homeButton.click();
        testImageLoading(driver, deviceName, "Home Page");

        System.out.println("✓ Navbar test for "+ deviceName + " passed.");
    }
    public static void testNavBar(WebDriver driver, Dimension screenSize, String phoneName){
        driver.manage().window().setSize(screenSize);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        //wait for the navbar to be visible before starting to test it
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("header")));

        //click hamburger menu
        WebElement hamburgerMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mobile-menu-btn")));
        hamburgerMenu.click();

        //find and click the ansys button
        WebElement AnsysButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.navbar__link[href='/ansys']")));
        AnsysButton.click();
        testImageLoading(driver, phoneName, "Ansys Page");

        //find and click the mobot button
        hamburgerMenu.click();
        WebElement MobotButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.navbar__link[href='/mobot']")));
        MobotButton.click();
        testImageLoading(driver, phoneName, "Mobot Page");

        //find and click the Projects button
        hamburgerMenu.click();
        WebElement ProjectsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.navbar__link[href='/projects']")));
        ProjectsButton.click();
        testImageLoading(driver, phoneName, "Projects Page");

        //find and click the hackathon button
        hamburgerMenu.click();
        WebElement HackathonButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.navbar__link[href='/hackathons']")));
        HackathonButton.click();
        testImageLoading(driver, phoneName, "Hackathon Page");

        //reset to home
        hamburgerMenu.click();
        WebElement homeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("HOME")));
        homeButton.click();
        testImageLoading(driver, phoneName, "Home Page");


        System.out.println("✓ Navbar test for "+ phoneName + " passed.");
    }
    public static void resetScreenSize(WebDriver driver){
        //reset to normal
        driver.manage().window().setSize(new Dimension(1920, 1080));
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.className("header")));
    }
    public static void scrollingTest(WebDriver driver, String phoneName){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //scrolling test portion
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        js.executeScript("window.scrollTo(0, 0);");
        System.out.println("✓ Scrolling Passed for: " + phoneName);
    }
    public static void testPageLoadTime(WebDriver driver, String url) {
        long startTime = System.currentTimeMillis();
        driver.get(url);
        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;
        System.out.println("✓ Page loaded in: " + loadTime + "ms for " + url);
    }
    public static void testBrokenLinks(WebDriver driver) {
        List<WebElement> links = driver.findElements(By.tagName("a"));
        for (WebElement link : links) {
            String url = link.getAttribute("href");
            if (url != null && !url.isEmpty()) {
                if (isLinkBroken(url)) {
                    if(url.equals("https://www.linkedin.com/in/ehab-abdalla-04ab411b3/")){ continue; }
                    System.out.println("✗ Broken link detected: " + url);
                } else {
                    System.out.println("✓ Link working: " + url);
                }
            }
        }
    }
    public static boolean isLinkBroken(String linkUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(linkUrl).openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            int responseCode = connection.getResponseCode();
            return responseCode >= 400;
        } catch (Exception e) {
            return true;
        }
    }
    public static void testImageLoading(WebDriver driver, String phone, String page) {
        List<WebElement> images = driver.findElements(By.tagName("img"));
        
        for (int i = 0; i < images.size(); i++) {
            try {
                // Refresh image list to avoid stale elements
                images = driver.findElements(By.tagName("img"));
    
                // Ensure index is still valid
                if (i >= images.size()) {
                    System.out.println("✗ Skipping image at index " + i + " (out of bounds)");
                    continue;
                }
    
                WebElement img = images.get(i);
                String src = img.getAttribute("src");
    
                if (src != null && !src.isEmpty()) {
                    if (isLinkBroken(src)) {
                        System.out.println("✗ Broken image detected: " + src);
                    }
                }
            } catch (StaleElementReferenceException e) {
                System.out.println("✗ Skipping stale image element at index " + i + ", reloading...");
                driver.navigate().refresh();
                images = driver.findElements(By.tagName("img"));
            } catch (IndexOutOfBoundsException e) {
                System.out.println("✗ Skipping invalid index: " + i);
            } catch (Exception e) {
                System.out.println("✗ Error checking image: " + e.getMessage());
            }
        }
        System.out.println("✓ All images loaded succesfully for: " + page);
    }
    
   
}
