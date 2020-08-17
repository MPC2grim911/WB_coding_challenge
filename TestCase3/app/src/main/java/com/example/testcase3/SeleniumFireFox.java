package com.example.testcase3;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.remote.BrowserType.CHROME;
import static org.openqa.selenium.remote.BrowserType.FIREFOX;
import static org.openqa.selenium.remote.BrowserType.IE;

public class SeleniumFireFox {
    //Future FIXME note: This may not be the proper format for Selenium searching
    //FIXME TODO: test code and verify method through research

    //WebDriver driver = new FirefoxDriver();
    //Search and checking contents
    private final static String s = "Tenet Movie Warner Brothers";
    private final static String check = "Tenet is an upcoming spy film written, produced, and directed by Christopher Nolan. It is a co-production between the United Kingdom and United States, and stars John David Washington, Robert Pattinson, Elizabeth Debicki, Dimple Kapadia, Michael Caine, and Kenneth Branagh.";

    //change these two as needed //FIXME: change might be needed for this section
    private final static String webEname = "q"; //id name of search function on current google homepage html
    private final static String webDesc = "kno-rdesc"; //class name of text with plot on google search html

    public static String res; //return results
    public static boolean searchResult;

    public static void main(String[] args) {

        WebDriver driver = new FirefoxDriver();

        driver.get("http://www.google.com");

        WebElement element = driver.findElement(By.name(webEname)); //search website
        element.sendKeys(s); // send also a "\n"
        element.submit();


        // wait until the google page shows the result
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));


        //get search results, specificly the description class
        WebElement resultsPanel = driver.findElement(By.className(webDesc));

        res = resultsPanel.getText(); //Check if text return
        if(res.contains(check)){
            System.out.println("True");
            searchResult = true;
        }
        else{
            System.out.println("False");
            searchResult = false;
        }

        /* //This version searches through the text in all the links provided by search
        List<WebElement> findElements = resultsPanel.findElements(By.xpath("//*[@id='rso']//h3/a"));

        // this are all the links you like to visit
        for (WebElement webElement : findElements)
        {
            String W = webElement.getText();
            if(W.contains(check)){
                System.out.println("True");
                break;
            }
        }*/
        driver.close();
    }
    /*public static DesiredCapabilities getBrowserCapabilities(String driverParameter, boolean headless) {
        DesiredCapabilities capabilities = null;
        if (driverParameter == null || driverParameter.equalsIgnoreCase(FIREFOX)) {
            capabilities = DesiredCapabilities.firefox();
            FirefoxOptions options = new FirefoxOptions();
            options.setHeadless(headless);
            capabilities.merge(options);
        }
        else if (driverParameter.equalsIgnoreCase(IE)) {
            capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        }
        else if (driverParameter.equalsIgnoreCase(CHROME)) {
            capabilities = DesiredCapabilities.chrome();
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(headless);
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            capabilities.merge(options);
        }
        return capabilities;
    }

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities ff = getBrowserCapabilities("FIREFOX");
        DesiredCapabilities c = getBrowserCapabilities("CHROME")

        capabilities.setCapability(CapabilityType.BROWSER_NAME, "FireFox");
        capabilities.setCapability(CapabilityType.VERSION, "79.0");
        capabilities.setCapability("platform", "win10"); // If this cap isn't specified, it will just get any available one

        capabilities.setCapability("build", "LambdaTestSampleApp");
        capabilities.setCapability("name", "LambdaTestJavaSample");
        capabilities.setCapability("network", true); // To enable network logs
        capabilities.setCapability("visual", true); // To enable step by step screenshot
        capabilities.setCapability("video", true); // To enable video recording
        capabilities.setCapability("console", true); // To capture console logs

        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, APP_PACKAGE_NAME);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, APP_ACTIVITY_NAME);


        System.setProperty("webdriver.firefoxtest.driver", "C:\\FirFoxDriverServerTest.exe");

        driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

    }*/

    /*@Test
    public void searchFireFox() {

        driver.manage().window().maximize();

        driver.get("http://google.com");

        //search

        System.out.println("Test completed successfully");
    }

    @After
    public void End() {

        driver.quit();
    }*/
}
