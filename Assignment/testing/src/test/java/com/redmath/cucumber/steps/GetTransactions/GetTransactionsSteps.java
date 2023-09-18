package com.redmath.cucumber.steps.GetTransactions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.List;

public class GetTransactionsSteps {
    private final WebDriver driver;

    @Autowired
    public GetTransactionsSteps(WebDriver driver) {
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

    @Given("the user is on transactions page with ID {string}")
    public void the_user_is_on_transactions_page_with_ID(String accountID) {
        // Construct the URL to navigate to the transactions page for the specified account ID
        String transactionsPageUrl = "http://localhost:4200/view-transactions/" + accountID;
        driver.get(transactionsPageUrl);
    }

    @Then("the user should see a list of transactions")
    public void the_user_should_see_a_list_of_transactions() {
        boolean allDataMatch = true;

        // Define the expected transaction data here
        String[][] expectedData = {
                {"1", "2023-01-01", "Online Purchase", "1000", "1"},
                {"2", "2023-01-01", "Online Purchase", "5000", "0"},
        };

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("table")));

        // Locate the rows within the table's tbody
        List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));
//        int numberOfRows = rows.size();
//        System.out.println("Number of rows: " + numberOfRows);

        for (int i = 0; i < expectedData.length; i++) {
            WebElement row = rows.get(i);
            List<WebElement> columns = row.findElements(By.xpath(".//td"));

            for (int j = 0; j < expectedData[i].length; j++) {
                String actualText = columns.get(j).getText().trim();
                String expectedText = expectedData[i][j].trim();
//                System.out.println("Actual data " + actualText);
//                System.out.println("Expected data   "  + expectedText);
                if (!actualText.equals(expectedText)) {
                    allDataMatch = false;
                    System.out.println("Mismatch: Data mismatch in row " + (i + 1) + ", column " + (j + 1));
                    break;
                }
            }

            if (!allDataMatch) {
                break;
            }
        }

        if (allDataMatch) {
            System.out.println("Success: All transaction data matches expected data");
        } else {
            System.out.println("Failure: Transaction data mismatch found");
        }
    }
}
