Feature: Successfully run the Places API

	@E2E @Regression
  Scenario Outline: Places scenario Add Get Put Delete
    Given The AddPlace api payload is ready "<name>" "<language>" "<address>"
    When "AddPlaceAPI" is triggered with "post" method
    Then Response should contain "status" as "OK" for "AddPlaceAPI"
    Then Response should contain "scope" as "APP" for "AddPlaceAPI"
    And placeId is successfully generated for "GetPlaceAPI" with "get" method
    Then placeId is successfully generated to validate "name" field with value "<name>" using "GetPlaceAPI"
    Then placeId is successfully generated to validate "language" field with value "<language>" using "GetPlaceAPI"
    Then placeId is successfully generated to validate "address" field with value "<address>" using "GetPlaceAPI"
    And Use placeId to update the "<website>" "<phone_number>" "<newlanguage>" "<newAddress>" using "UpdatePlaceAPI" and "put" method
    Then Response Header should contain "statusCode" as "200" for "AddPlaceAPI"
    Then Response should contain "msg" as "Address successfully updated" for "UpdatePlaceAPI"
    And placeId is successfully generated for "DeletePlaceAPI" with "delete" method
    Then Response should contain "status" as "OK" for "DeletePlaceAPI"

    Examples: 
      | name     | language | address             | website     | phone_number    | newlanguage | newAddress                   |
      | Pagalpur | Hindi    | 4346 Nahargarh Road | www.abc.com | (+91)8589029876 | French      | Nayyan House Thrissur, India |
			| Pagalpur2 | Hindi 2   | 4346 Nahargarh Road2 | www.abc.com2 | (+91)85890298761 | French      | Nayyan House Thrissur, India |
      
      @Delete @Regression
      Scenario Outline: Delete Place using delete place API
     And placeId is successfully generated for "DeletePlaceAPI" with "delete" method
    Then Response should contain "status" as "OK" for "DeletePlaceAPI"

    Examples: 
      | name     | language | address             | website     | phone_number    | newlanguage | newAddress                   |
      | Pagalpur | Hindi    | 4346 Nahargarh Road | www.abc.com | (+91)8589029876 | French      | Nayyan House Thrissur, India |

      