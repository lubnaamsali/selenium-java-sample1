package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.DriverFactory;

public class LoginTest {

    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setup() {

        driver = DriverFactory.getDriver();
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
    }

    // TC_001 - Valid Login
    @Test(priority = 1)
    public void validLogin() {

        loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    // TC_002 - Invalid Password
    @Test(priority = 2)
    public void invalidPassword() {

        loginPage.login("standard_user", "wrong_password");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service");
    }

    // TC_003 - Invalid Username
    @Test(priority = 3)
    public void invalidUsername() {

        loginPage.login("wrong_user", "secret_sauce");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service");
    }

    // TC_004 - Empty Username
    @Test(priority = 4)
    public void emptyUsername() {

        loginPage.login("", "secret_sauce");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username is required");
    }

    // TC_005 - Empty Password
    @Test(priority = 5)
    public void emptyPassword() {

        loginPage.login("standard_user", "");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Password is required");
    }

    // TC_006 - Empty Username and Password
    @Test(priority = 6)
    public void emptyUsernameAndPassword() {

        loginPage.login("", "");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username is required");
    }

    // TC_007 - Locked User
    @Test(priority = 7)
    public void lockedUserLogin() {

        loginPage.login("locked_out_user", "secret_sauce");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Sorry, this user has been locked out.");
    }

    @AfterMethod
    public void tearDown() {

        driver.quit();

    }
}