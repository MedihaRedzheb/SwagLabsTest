Feature: Shopping Cart Menu

  Scenario: Add one item to shopping cart

    Given User is on Home Page

    And user is log in

    And Shopping cart menu is visible

    And Add one item in shopping cart

    Then In Shopping Cart have one item

    And Delete Cookies

  Scenario: Add two item to shopping cart

    Given User is on Home Page

    And user is log in

    And Shopping cart menu is visible

    And Add two item in shopping cart

    Then In Shopping Cart have two item

    And Delete Cookies
