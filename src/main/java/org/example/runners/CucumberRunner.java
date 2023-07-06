package org.example.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
//        tags = "",
        features = "src/test/resources/features/",
        glue = {"org/example/stepDefinitions"},
        plugin = {"pretty", "json:target/cucumber-reports/cucumber.json"})

public class CucumberRunner extends AbstractTestNGCucumberTests {

}