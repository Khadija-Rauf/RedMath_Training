package com.redmath.cucumber.steps.CreateAccount;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class CreateAccountSteps {
    private final WebDriver driver;

    @Autowired
    public CreateAccountSteps(WebDriver driver) {
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
    @Given("the user is on the create account page")
    public void the_user_is_on_the_create_account_page() {
        driver.get("http://localhost:4200/create-account");
    }
    public void createAccount(String name, String password, String email, String address) {
        WebElement nameInput = driver.findElement(By.id("name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement emailInput = driver.findElement(By.id("email"));
        WebElement addressInput = driver.findElement(By.id("address"));
        nameInput.sendKeys(name);
        passwordInput.sendKeys(password);
        emailInput.sendKeys(email);
        addressInput.sendKeys(address);
    }

    @When("the user enters the following account details:")
    public void the_user_enters_the_following_account_details(Map<String, String> accountDetails) {
        String name = accountDetails.get("Name");
        String password = accountDetails.get("Password");
        String email = accountDetails.get("Email");
        String address = accountDetails.get("Address");

        createAccount(name, password, email, address);
    }
    @When("hits create")
    public void hits_submit() {
        WebElement submitButton = driver.findElement(By.cssSelector("input[type='submit'][value='Create'][class='btn btn-success']"));
        submitButton.click();
    }
    @Then("the user should see a success message")
    public void the_user_should_see_a_success_message() {
        System.out.println("Done successfully");
    }

    @Then("the new account should be listed on the accounts page")
    public void the_new_account_should_be_listed_on_the_accounts_page() {
        WebElement accountsTable = driver.findElement(By.className("table"));
        List<WebElement> rows = accountsTable.findElements(By.xpath(".//tbody/tr"));
        String newUsername = "Anusha";
        boolean accountFound = false;
        for (WebElement row : rows) {
            String usernameColumnText = row.findElement(By.xpath(".//td[2]")).getText().trim();
//            System.out.println(usernameColumnText);
            if (usernameColumnText.equals(newUsername)) {
                accountFound = true;
                break;
            }
        }
        Assert.assertTrue(accountFound,"New account is not listed on the accounts page");
        System.out.println("New account is not listed on the accounts page!!");
    }
}
