package com.redmath.cucumber.steps.GetAccounts;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class GetAccountsSteps {
    private final WebDriver driver;

    @Autowired
    public GetAccountsSteps(WebDriver driver) {
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
    @Given("the user is on accounts page")
    public void the_user_is_on_accounts_page() {
        driver.get("http://localhost:4200/accounts");
    }

    @Then("the user should see a list of accounts")
    public void the_user_should_see_a_list_of_accounts() {
        boolean allDataMatch = true;
        String[][] expectedData = {
                {"1",	"Aila",	"**********",	"ailanoah81@gmail.com",	"Sydney, Australia",	"USER",	"View"},
                {"2" ,"Admin", "***********","adanfiaz111@gmail.com", "Coventry, Central England" ,"ADMIN", "View"},
                {"3",	"John",	"**********",	"Johndoe21@gmail.com",	"Bay Area,San Francisco,CA",	"USER",	"View"	}
        };
        WebElement table = driver.findElement(By.className("table"));
        List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));
        int numberOfRows = rows.size();
//        System.out.println("Number of rows: " + numberOfRows);
        for (int i = 0; i < expectedData.length; i++) {
            WebElement row = rows.get(i);
            List<WebElement> columns = row.findElements(By.xpath(".//td"));
            for (int j = 0; j < expectedData[i].length; j++) {
                if (j == 2) {
                    continue;
                }
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
                System.out.println("Success: All data matches expected data");
            } else {
                System.out.println("Failure: Data mismatch found");
            }
        }

}
