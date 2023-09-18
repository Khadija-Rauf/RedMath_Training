package com.redmath.cucumber.steps.DeleteAccount;

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
import java.util.concurrent.TimeUnit;

public class DeleteAccountSteps {
    private final WebDriver driver;

    @Autowired
    public DeleteAccountSteps(WebDriver driver) {
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
    @When("the user deletes the account with ID {string}")
    public void deleteAccountWithID(String accountID) {
        WebElement table = driver.findElement(By.className("table"));
        List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));
        for (WebElement row : rows) {
            WebElement accountIDCell = row.findElement(By.xpath(".//td[1]"));
            if (accountIDCell.getText().trim().equals(accountID)) {
                WebElement deleteButton = row.findElement(By.cssSelector("button.text-danger"));
                deleteButton.click();
                break;
            }
        }
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Then("the account with ID {string} should be deleted")
    public void verifyAccountDeleted(String accountID) {
        WebElement table = driver.findElement(By.className("table"));
        List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));
        for (WebElement row : rows) {
            WebElement accountIDCell = row.findElement(By.xpath(".//td[1]"));
            if (accountIDCell.getText().trim().equals(accountID)) {
                Assert.fail("Account with ID " + accountID + " is still present.");
            }
        }
        System.out.println("User deleted successfully!!");
    }

}
