/**
 * Created by Yuchen Qian on 7/30/17.
 */

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FenbyRegistrationTest {
    private WebDriver driver;
    private String baseUrl = "http://www.fenby.com/";

    //Note: Although some error messages are the same, I created separate variables for them cuz error messages might change
    private String expectedErrorMessageForEmailRegistered = "邮箱地址无效或重复";
    private String expectedErrorMessageForIncorrectEmailFormat = "邮箱地址无效或重复";
    private String expectedErrorMessageForEmptyEmail = "这个字段是必填项。";

    //There's a bug of not showing error message, so I put value "TBD" to this var. Pls check 'Bug 2' in the following comment
    private String expectedErrorMessageForEmptyPassword = "TBD。";
    private String expectedErrorMessageForEmptyPasswordConfirm = "这个字段是必填项。";

    //There's a bug of not showing error message, so I put value "TBD" to this var. Pls check 'Bug 3' in the following comment
    private String expectedErrorMessageForPasswordNotMatch = "TBD";
    private String expectedErrorMessageForPasswordTooShort = "Ensure this value has at least 3 characters";

    @Test
    /*Postive case 1: register successfully*/
    public void registerSuccess(){
        System.setProperty("webdriver.gecko.driver", "/Users/yuqian/Documents/Tools/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(baseUrl);
        driver.findElement(By.cssSelector("button.btn.btn-danger")).click();

        driver.findElement(By.id("id_email")).clear();
        driver.findElement(By.id("id_email")).sendKeys(generateRandomEmail());
        driver.findElement(By.id("id_password1")).sendKeys("123");
        driver.findElement(By.id("id_password2")).sendKeys("123");
        driver.findElement(By.xpath("/html/body/section[2]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/form/button")).click();

        //if user registers successfully, then he/she will be automatically logged in with an avatar button on the upper-right corner
        Assert.assertTrue(driver.findElements(By.cssSelector("a.dropdown-toggle span.ng-binding")).size() != 0);

        driver.close();
        driver.quit();
    }

    @Test
    /*Negative case 1: register with an email which has already been registered*/
    /*Bug 1: there's a bug in this site that it doesn't have enough validation. As a result, it allows email input like "lu@test"*/
    public void emailAlreadyRegistered(){
        System.setProperty("webdriver.gecko.driver", "/Users/yuqian/Documents/Tools/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(baseUrl);
        driver.findElement(By.cssSelector("button.btn.btn-danger")).click();

        driver.findElement(By.id("id_email")).clear();
        driver.findElement(By.id("id_email")).sendKeys("lu@test.com");
        driver.findElement(By.id("id_password1")).sendKeys("123");
        driver.findElement(By.id("id_password2")).sendKeys("123");
        driver.findElement(By.xpath("/html/body/section[2]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/form/button")).click();

       //Assert error message expectedErrorMessageForIncorrectEmailFormat appears
       Assert.assertTrue(driver.findElement(By.xpath("/html/body/section[2]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/form/div[2]/ul[1]/li[3]")).getText().contains(expectedErrorMessageForIncorrectEmailFormat));

        driver.close();
        driver.quit();
    }

    @Test
    /*Negative case 2: register with an incorrect email format*/
    public void emailFormatIncorrect(){
        System.setProperty("webdriver.gecko.driver", "/Users/yuqian/Documents/Tools/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(baseUrl);
        driver.findElement(By.cssSelector("button.btn.btn-danger")).click();

        driver.findElement(By.id("id_email")).clear();
        driver.findElement(By.id("id_email")).sendKeys("lu");
        driver.findElement(By.id("id_password1")).sendKeys("123");
        driver.findElement(By.id("id_password2")).sendKeys("123");
        driver.findElement(By.xpath("/html/body/section[2]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/form/button")).click();

        //Assert error message expectedErrorMessageForEmailRegistered appears
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/section[2]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/form/div[2]/ul[1]/li[3]")).getText().contains(expectedErrorMessageForEmailRegistered));

        driver.close();
        driver.quit();
    }

    @Test
    /*Negative case 3: register with email field not filled*/
    public void emailFieldNotFilled(){
        System.setProperty("webdriver.gecko.driver", "/Users/yuqian/Documents/Tools/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(baseUrl);
        driver.findElement(By.cssSelector("button.btn.btn-danger")).click();

        driver.findElement(By.id("id_password1")).sendKeys("123");
        driver.findElement(By.id("id_password2")).sendKeys("123");
        driver.findElement(By.xpath("/html/body/section[2]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/form/button")).click();

        //Assert error message expectedErrorMessageForEmptyEmail appears
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/section[2]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/form/div[2]/ul[1]/li[1]")).getText().contains(expectedErrorMessageForEmptyEmail));

        driver.close();
        driver.quit();
    }

    @Test
    /*Negative case 4: register with password field not filled*/
    /*Bug 2: this case fails because there's no error message is password field is emtpy.*/
    public void passwordFieldNotFilled(){
        System.setProperty("webdriver.gecko.driver", "/Users/yuqian/Documents/Tools/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(baseUrl);
        driver.findElement(By.cssSelector("button.btn.btn-danger")).click();

        driver.findElement(By.id("id_email")).clear();
        driver.findElement(By.id("id_email")).sendKeys(generateRandomEmail());
        driver.findElement(By.id("id_password1")).sendKeys("123");
        driver.findElement(By.id("id_password2")).sendKeys("123");
        driver.findElement(By.xpath("/html/body/section[2]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/form/button")).click();

        //Assert error message expectedErrorMessageForEmptyPassword appears
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/section[2]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/form/div[3]/ul[1]/li[2]")).getText().contains(expectedErrorMessageForEmptyPassword));

        driver.close();
        driver.quit();
    }

    @Test
    /*Negative case 5: register with password confirm field not filled*/
    public void passwordConfirmFieldNotFilled(){
        System.setProperty("webdriver.gecko.driver", "/Users/yuqian/Documents/Tools/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(baseUrl);
        driver.findElement(By.cssSelector("button.btn.btn-danger")).click();

        driver.findElement(By.id("id_email")).clear();
        driver.findElement(By.id("id_email")).sendKeys(generateRandomEmail());
        driver.findElement(By.id("id_password1")).sendKeys("123");
        driver.findElement(By.id("id_password2")).sendKeys("123");
        driver.findElement(By.xpath("/html/body/section[2]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/form/button")).click();

        //Assert error message expectedErrorMessageForEmptyPasswordConfirm appears
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/section[2]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/form/div[4]/ul[1]/li[1]")).getText().contains(expectedErrorMessageForEmptyPasswordConfirm));

        driver.close();
        driver.quit();
    }

    @Test
    /*Negative case 6: register with password fields unmatched*/
    /*Bug 3: this case fails cuz there's no error message shown if the password confirm field value doesn't match the password field value.*/
    public void unmatchedPasswordFields(){
        System.setProperty("webdriver.gecko.driver", "/Users/yuqian/Documents/Tools/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(baseUrl);
        driver.findElement(By.cssSelector("button.btn.btn-danger")).click();

        driver.findElement(By.id("id_email")).clear();
        driver.findElement(By.id("id_email")).sendKeys(generateRandomEmail());
        driver.findElement(By.id("id_password1")).sendKeys("123");
        driver.findElement(By.id("id_password2")).sendKeys("12345");
        driver.findElement(By.xpath("/html/body/section[2]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/form/button")).click();

        //Assert error message expectedErrorMessageForPasswordNotMatch appears
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/section[2]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/form/div[4]/ul[1]/li[1]")).getText().contains(expectedErrorMessageForPasswordNotMatch));

        driver.close();
        driver.quit();

    }

    @Test
    /*Negative case 7: register with a password which doesn't conform to the rules, aka. less than 3 characters*/
    public void passordNotConformingToRules(){
        System.setProperty("webdriver.gecko.driver", "/Users/yuqian/Documents/Tools/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(baseUrl);
        driver.findElement(By.cssSelector("button.btn.btn-danger")).click();

        driver.findElement(By.id("id_email")).clear();
        driver.findElement(By.id("id_email")).sendKeys(generateRandomEmail());
        driver.findElement(By.id("id_password1")).sendKeys("1");
        driver.findElement(By.id("id_password2")).sendKeys("1");
        driver.findElement(By.xpath("/html/body/section[2]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/form/button")).click();

        //Assert error message expectedErrorMessageForPasswordTooShort appears
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/section[2]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/form/div[4]/ul[1]/li[1]")).getText().contains(expectedErrorMessageForEmptyPasswordConfirm));

        driver.close();
        driver.quit();
    }

    public String generateRandomEmail(){
        return UUID.randomUUID().toString() + "@test.com";
    }

}
