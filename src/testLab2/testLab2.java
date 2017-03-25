package testLab2;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import com.csvreader.CsvReader;

public class testLab2 {
  public static WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  @Before
  public void setUp() throws Exception {
	System.setProperty("webdriver.firefox.marionette", "webDriver//geckodriver.exe");
    driver = new FirefoxDriver();
    baseUrl = "http://121.193.130.195:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void test() throws Exception {
    CsvReader students = new CsvReader("../inputgit.csv", ',',Charset.forName("GBK"));
    students.readHeaders();
    while (students.readRecord()){
    	driver.get(baseUrl);
    	String sid = students.get("—ß∫≈");
    	String spwd = sid.substring(4);
    	String givenUrl = students.get("githubµÿ÷∑");
    	System.out.println(givenUrl);
    	driver.findElement(By.id("name")).clear();
    	driver.findElement(By.id("name")).sendKeys(sid);
    	driver.findElement(By.id("pwd")).clear();
    	driver.findElement(By.id("pwd")).sendKeys(spwd);
    	driver.findElement(By.id("submit")).click();
    	WebElement element = driver.findElement(By.xpath("//*[@id=\"table-main\"]/tr[3]/td[2]"));
    	String url = element.getText();
    	System.out.println(url);
    	if (!url.equals(givenUrl)){
    		System.out.println("failure");
    		return;
    	}
    }
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}