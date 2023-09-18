package com.redmath.cucumber.steps.UpdateAccount;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class UpdateAccountSteps {
    private final WebDriver driver;

    @Autowired
    public UpdateAccountSteps(WebDriver driver) {
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
    public void updateAccount(String name, String password, String email, String address) {
        WebElement nameInput = driver.findElement(By.id("name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement emailInput = driver.findElement(By.id("email"));
        WebElement addressInput = driver.findElement(By.id("address"));
        nameInput.sendKeys(name);
        passwordInput.sendKeys(password);
        emailInput.sendKeys(email);
        addressInput.sendKeys(address);
    }
    @When("the user selects the account with ID {string}")
    public void updateAccountWithID(String accountID) {
        WebElement table = driver.findElement(By.className("table"));
        List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));
        for (WebElement row : rows) {
            WebElement accountIDCell = row.findElement(By.xpath(".//td[1]"));
            if (accountIDCell.getText().trim().equals(accountID)) {
                WebElement updateButton = row.findElement(By.cssSelector("button.text-primary"));
                updateButton.click();
                break;
            }
        }
    }
    @When("the user enters valid updated account details")
    public void the_user_enters_the_following_account_details(Map<String, String> accountDetails) {
        driver.get("http://localhost:4200/update-account/3");
        String name = accountDetails.get("Name");
        String password = accountDetails.get("Password");
        String email = accountDetails.get("Email");
        String address = accountDetails.get("Address");

        updateAccount(name, password, email, address);
    }
    @When("hits update")
    public void hits_submit() {
        WebElement updateButton = driver.findElement(By.cssSelector("button.btn.btn-success[type='submit']"));
        updateButton.click();
    }
    @Then("the new updated_account should be listed on the accounts page")
    public void the_new_account_should_be_listed_on_the_accounts_page() {
        WebElement accountsTable = driver.findElement(By.className("table"));
        List<WebElement> rows = accountsTable.findElements(By.xpath(".//tbody/tr"));
        String newUsername = "Anusha";
        boolean accountFound = false;
        for (WebElement row : rows) {
            String usernameColumnText = row.findElement(By.xpath(".//td[2]")).getText().trim();
//            System.out.println(usernameColumnText);
            if (!usernameColumnText.equals(newUsername)) {
                accountFound = true;
                break;
            }
        }
        Assert.assertTrue(accountFound,"Updated account is listed on the accounts page");
        System.out.println("Updated account is listed on the accounts page!!");
    }
}
