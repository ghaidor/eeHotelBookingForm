# Hotel Booking Form

This project runs UI automated tests for creating & deleting hotel bookings for a hotel.

The browser tests are meant to be run against a fully deployed instance of Hotel Booking Form. These tests are then
executed to hit a browser (of choice) and automate some of the more common operations that will mimic how a user 
may actually use the system. More involved tests can be added without much rework due to the framework design.

##Test Execution

When executing the tests the webdrivermanager will check for the drivers and install the appropriate drivers according to the browser chosen and OS.
The automated tests can be run in any IDE although intelliJ is preferred for minimum 
set-up as the Junit runner is automatically configured for execution.

### Running tests in Chrome/FireFox

The tests can be run either in Chrome (chromedriver) or Firefox (geckodriver) which can be specified in the
run configuration with [browser] env variable set to either "chrome" or "firefox". 
IntelliJ default run config is set to chrome.


## General Design Principles

The UI tests are written with the page object model structure utilising the JUnit framework.

The Page Object Layer - Contains the lower level code which actually makes use of the Selenium framework
to either enter text in the fields and base layer to handle the interactions with the DOM.

## Versions

Selenium - 3.14.0
JUnit - 4.12

