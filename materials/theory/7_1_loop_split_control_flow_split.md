# Loop Split
### Precondition
A loop with several responsibilities / concerns, where initially a given data holder is created and that is used for many different, independent calculations.

### Example
#### Before the split
````java
// calculate fee, frequent renter points, and document to display in front end
int frequentRenterPoints = 0;
String result = "Rental Record for " + customer.getName() + "\n";
double totalAmount = 0;

for (int i = 0; i < rentBooksRequests.size(); i++) {
    // concern 1: create rental
    var rentalData = rentBooksRequests.get(i).split(" ");
    var bookId = Integer.parseInt(rentalData[0]);
    
    Book book = bookRepository.findById(bookId);
    var daysRented = Integer.parseInt(rentalData[1]);
    Rental rental = new Rental(book, daysRented);
    
    // concern 2: calculate frequent renter points
    frequentRenterPoints += rental.getFrequentRenterPoints();
    
    // concern 3: create figures for this rental
    result += "\t'" + rental.getBookTitle() + "' by '" + rental.getBookAuthors() + "' for " + rental.getDaysRented() + " days: \t" + rental.getAmount() + " $\n";
    
    // concern 4: calculate total amount
    totalAmount += rental.getAmount();
}
````

#### After
```java
// calculate fee, frequent renter points, and document to display in front end
int frequentRenterPoints = 0;
String result = "Rental Record for " + customer.getName() + "\n";
double totalAmount = 0;

var rentals = new ArrayList<>();

// concern 1: create and add rental to a list in the first loop.
for (int i = 0; i < rentBooksRequests.size(); i++) {
    final String[] rentalData = rentBooksRequests.get(i).split(" ");
    
    int bookId = Integer.parseInt(rentalData[0]);
    Book book = bookRepository.findById(bookId);
    
    int daysRented = Integer.parseInt(rentalData[1]);
    Rental rental = new Rental(book, daysRented);
    rentals.add(rental);
}

// concern 2: calculate frequent renter points
for(Rental rental: rentals){
    frequentRenterPoints += rental.getFrequentRenterPoints();
}

// concern 3: create figures for this rental
for(Rental rental: rentals){
    result+="\t'"+rental.getBookTitle()+"' by '"+rental.getBookAuthors()+"' for "+rental.getDaysRented()+" days: \t"+rental.getAmount()+" $\n";
}

// concern 4: calculate total amount
for(Rental rental: rentals) {
    totalAmount += rental.getAmount();
}
```
1. Identify and separate concerns into collocated blocks of code within the initial large loop
2. In the first loop, create a list of the objects that are used for various calculations
2. For every concern, add an individual loop (e.g. a foreach)
3. Move every concern to its own loop
4. Optional: wrap each loop into its own method and move it to an own file or class


# Control Flow Split
- The Loop Split algorithm of duplicating the control structure is also feasible for other control structures (e.g. if, switch) with several separate concerns in the body.
- identifying different concerns and duplicating the control flow structure for each concern enables the extraction of methods with only one purpose and a single responsibility.
- Simple, small methods/functions that can be moved close to the data they use (or into a class) are the main driver of a rich, understandable, extensible domain model and maintainable software design.
