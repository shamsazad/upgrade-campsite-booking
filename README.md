# Read Me First
To `Run` this project `clone` it your local system and use `maven`.

# Getting Started

## Project Requirements:
* You can book maximum of 3 days in one booking.
* You need to book at-least one day in advance.
* Booking date cannot be in the past.
* You can book upto 1 month in advance.
* You can modify, search or delete a booking.

## Book a campsite
Run the project, use postman to enjoy the features. Hit the end-point,
`http://localhost:8080/booking/book-campsite` and use the following payload.
```json
{
 	"firstName" : "shams",
 	"lastName" : "azad",
 	"email" : "shams@azad.com",
 	"arrivalDate" : "2020-08-17",
 	"departureDate" : "2020-08-18"
 }
```
## Modify a booking
Call `http://localhost:8080/booking/update-booking` from postman with following
payload.

```json
{
    "bookingId": 1,
    "arrivalDate": "2020-08-15",
    "departureDate": "2020-08-16"
}
```

