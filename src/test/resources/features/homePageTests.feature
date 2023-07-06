@medi
Feature:Home Page

  Scenario: Login from My Home Page

    Given User is on Home Page

    And Fill User Name Button is visible

    And Fill Password Button is visible

    And Click on Log In button

    Then User is on My Account Page

    Then Delete Cookies

  Scenario: Logout from My Home Page

    Given User is on Home Page

    And user is log in

    And React Burger menu is visible

    And Logout button is visible

    Then User is logout successfully

