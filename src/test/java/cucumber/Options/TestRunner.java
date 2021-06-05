package cucumber.Options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
//import io.cucumber.core.cli.Main;
@RunWith(Cucumber.class)
@CucumberOptions(//dryRun=true,

plugin = {"pretty","json:target/cucumber-report.json"},

features = {"src/test/java/features"},

glue = {"stepDefinitions"}

)
public class TestRunner {
//"html:target/html-report",tags= "@Delete"
}
