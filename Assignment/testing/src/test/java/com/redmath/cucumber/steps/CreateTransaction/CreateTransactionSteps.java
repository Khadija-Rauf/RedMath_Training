package com.redmath.cucumber.steps.CreateTransaction;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

public class CreateTransactionSteps {
    private final WebDriver driver;

    @Autowired
    public CreateTransactionSteps(WebDriver driver) {
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
    @Given("the user is on the view account page with ID {string}")
    public void the_user_is_on_the_view_account_page(String accountID) {
        driver.get("http://localhost:4200/accounts");
        WebElement table = driver.findElement(By.className("table"));
        List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));
        for (WebElement row : rows) {
            WebElement accountIDCell = row.findElement(By.xpath(".//td[1]"));
            if (accountIDCell.getText().trim().equals(accountID)) {
                WebElement updateButton = row.findElement(By.cssSelector("button.text-success"));
                updateButton.click();
                break;
            }
        }
    }
    public void createTransaction(String description, String amount, String indicator) {
        WebElement descriptionInput = driver.findElement(By.id("password"));
        WebElement amountInput = driver.findElement(By.id("email"));
        WebElement indicatorInput = driver.findElement(By.id("address"));
        descriptionInput.sendKeys(description);
        amountInput.sendKeys(amount);
        indicatorInput.sendKeys(indicator);
    }

    @When("the user enters the following transaction details:")
    public void the_user_enters_the_following_transaction_details(Map<String, String> accountDetails) {
        driver.get("http://localhost:4200/create-transaction/2");
        String description = accountDetails.get("Description");
        String amount = accountDetails.get("Amount");
        String indicator = accountDetails.get("Indicator");

        createTransaction(description, amount, indicator);
    }
}

