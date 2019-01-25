# checkoutOrderTotalKata
A kata to showcase basic java with TDD. 
This kata represents an API that could be called by a point-of-sale system.
The API features functionality to scan items into an order and retrieve the resulting total.
It also supports several types of price specials. 
### Running Tests/Coverage
Running the below command from this project's root directory will run all tests.

```gradle clean test```

After running tests, the below reports will be availiable: 
* Test report: ```build/reports/tests/test/index.html```
* Coverage report: ```build/reports/jacoco/test/html/index.html```

### A note about the last requirement:
The final requirement of this kata said:
 
 ```Support "Buy N, get M of equal or lesser value for %X off" on weighted items.```
 
 I interpreted this requirement as follows: 
 If you buy a specific item N at its full price, you get a specific item M at an %X discount.
 
 This discount is limited to an amount of M that is worth the same price as the amount of N being purchased.
 This limit is calculated using the base (non-discounted) price of M.
 That way, you are limited to an amount of M that is has an equal or lesser value as N.
 
 As with limits on other price specials, any amount of M over the limit is priced at the base price.
  