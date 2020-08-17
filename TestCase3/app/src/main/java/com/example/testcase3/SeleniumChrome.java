package com.example.testcase3;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class SeleniumChrome {

    //FIXME TODO: check and compare Chrome browser's google homepage html with firefox's google html
    //FIXME TODO: research into Selenium Java for Chrome webdrivers
    //FIXME TODO: get proper webelement variables and capabilities to enable webdriver usage

    //constants (do not change)
    private final static String s = "Tenet Movie Warner Brothers";
    private final static String check = "Tenet is an upcoming spy film written, produced, and directed by Christopher Nolan. It is a co-production between the United Kingdom and United States, and stars John David Washington, Robert Pattinson, Elizabeth Debicki, Dimple Kapadia, Michael Caine, and Kenneth Branagh.";

    public static String res; //return results
    public static boolean searchResult;

    //FIXME: verify and modify all of below
    private final static String webEname = "q"; //change these two as needed
    private final static String webDesc = "kno-rdesc";



    WebDriver driver = new ChromeDriver();




    //@Before
    public static void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome");
        capabilities.setCapability(CapabilityType.VERSION, "77.0");
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, "win10"); // If this cap isn't specified, it will just get any available one

        capabilities.setCapability("build", "LambdaTestSampleApp");
        capabilities.setCapability("name", "LambdaTestJavaSample");

        capabilities.setCapability("network", true); // To enable network logs
        capabilities.setCapability("visual", true); // To enable step by step screenshot
        capabilities.setCapability("video", true); // To enable video recording
        capabilities.setCapability("console", true); // To capture console logs

        capabilities.setCapability("selenium_version","4.0.0-alpha-2");

        capabilities.setCapability("timezone","UTC+05:30");
        capabilities.setCapability("geoLocation","IN");
        capabilities.setCapability("chrome.driver","78.0");

        System.setProperty("webdriver.chrometest.driver", "C:\\ChromeDriverServerTest.exe");

        driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

    }

    //@Test
    public static void searchChrome() {

        driver.manage().window().maximize();

        driver.get("http://google.com");

        //search
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

    }

    //@After
    public static void End() {

        driver.quit();
    }

    public static void main(String[] args){
        setUp();
        searchChrome();
        End();
    }
}
