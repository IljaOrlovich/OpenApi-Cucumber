Feature: Author Management

  Scenario: List all authors
    When the client trying to get all authors
    Then the response status should be 200
    And the response should contain the following authors


  Scenario: Get an author by ID
    When the client requests to get author by id
    Then the response status should be 200
    And the response should contain the author with id 2 and name "Name2"

  Scenario: Add a new author
    When the client trying to create new author
      | name       |
      | New Author |
    Then the response status should be 201
    And the response should contain the author with id 2 and name "New Author"


  Scenario: Update an existing author
    Given the author repository is initialized with the following authors
      | id | name  |
      | 52  | Name1 |
    When the client trying to update author
      | name           |
      | Updated Author |
    Then the response status should be 200
    And the response should contain the author with id 52 and name "Updated Author"

  Scenario: Delete an author
    Given the author repository is initialized with the following authors
      | id | name  |
      | 553  | Name1 |
    When the client trying to delete authors
    Then the response status should be 204
    And the author with id 553 should not exist in the repository

