Feature:My Account Page

  Scenario: Is the inventory container visible

    Given User is on Home Page

    And user is log in

    And Inventory Container is visible

    Then Number of items in inventory container are 6

    Then User is logout

    Scenario: Filtering menu by name work correct

      Given User is on Home Page

      And user is log in

      And Inventory Container is visible

      And Product sort container is visible

      And Select "Name (Z to A)" from Product sort container

      Then Product are sorted by name

      Then User is logout

      Scenario: Filtering menu by price work correct

        Given User is on Home Page

        And user is log in

        And Inventory Container is visible

        And Product sort container is visible

        And Select "Price (low to high)" from Product sort container

        Then Product are sorted by price

        Then User is logout

