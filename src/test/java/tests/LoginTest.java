package tests;

import config.TestConfig;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BaseTest;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.Assert;
import pages.LoginPage;

import java.time.Duration;


public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
                {TestConfig.VALID_USERNAME, TestConfig.VALID_PASSWORD, true},        // успешный логин
                {TestConfig.INVALID_USERNAME, TestConfig.VALID_PASSWORD, false},       // неверный логин
        };
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, boolean isSuccessExpected) {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername(username)
                .enterPassword(password)
                .clickLogin();

        if (isSuccessExpected) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains("/dashboard"));
            Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"), "Login should be successful");
        } else {
            Assert.assertTrue(loginPage.isErrorDisplayed(), "The email address or password does not match any account. Please try again.");
        }
    }
}
