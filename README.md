# QBEAutomation

Program automates generating a quote for vehicle insurance.

While program is built to incorporate multiple different outcomes and conditions the main focus of test
is for anonymous quote only, so when other options at this stage are chosen the program tests end at to further the quote real world test data is needed and is currently not available.

Test can only run from command line as it requires two maven parameters to run.The command to run is below:
mvn test -Dbrowser="browserType" -DquoteType="quoteChoice"

 - browserType can either be chrome or firefox.
 - quoteChoice can be billing/plate, vNumber or anonymous
