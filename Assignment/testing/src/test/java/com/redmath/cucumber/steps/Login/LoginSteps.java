package com.redmath.cucumber.steps.Login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginSteps {
    private WebDriver driver;
    @Autowired
    public LoginSteps(WebDriver driver) {
        this.driver = driver;
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("the user is on the login page")
    public void the_user_is_on_login_page() {
        driver.get("http://localhost:4200/");
    }

    @When("the user enters valid credentials")
    public void the_user_enters_valid_credentials() {
        WebElement usernameInput = driver.findElement(By.id("name"));
        WebElement passwordInput = driver.findElement(By.id("password"));

        usernameInput.sendKeys("Admin");
        passwordInput.sendKeys("Admin");
    }

    @When("hits submit")
    public void hits_submit() {
        WebElement submitButton = driver.findElement(By.name("submit"));
        submitButton.click();
    }

    @Then("the user should be logged in successfully")
    public void the_user_should_be_logged_in_successfully() {
        System.out.println("User logged In successfully");
    }
}
