Feature: Insurance Quote - User is wanting to acquire a green slip quote
for their current vehicle.

Scenario: User wants to generate anonymous quote
Given I'm on the homepage
And I navigate to getting a quote in NSW
When I select anonymous quote
And I enter my vehicle details
And I enter insurance preferences
Then Once all detail entered I'll be provided with a quote