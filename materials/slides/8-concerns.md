# Can we always just move logic to other classes (Feature Envy)?

----
# What about architectural design patterns?

----
# Layered Architecture (DDD style)
* Presentation Layer
* Business Layer
  * Sometimes, the business layer additionally gets divided into application and domain layers.
* Data Access Layer

----
# Presentation Layer
* Receives (and validates) user input
* Calls business logic through "application services"
* May or may not access domain objects directly
* Presents results to the user
* Maps data structures from application services to presentation layer

----
# Data access layer
* Handles data access (DB, file system, REST APIs, etc.)
* Called from application services
* May or may not access domain objects directly
* Does not access the presentation layer directly

----
# Application Layer
* Coordinates the "dance of domain objects"
* Fetches data from the data access, executes building blocks, and updates the data access
* May or may not access domain objects directly
* Does not access the presentation layer directly
* 
----
# Domain Layer
* Core business logic
* Depending on the pattern used, often no access to any other layer

----
# Exercise - Concern Separation

----

# Find Book by Title

```java
public Book findBookByTitle(String title) throws SQLException {
    String query = "SELECT * FROM books WHERE title = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, title);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getBoolean("is_borrowed")
            );
        }
    }
    return null;
}
```

---
# Borrow Book

```java
public boolean borrowBook(String bookTitle, String memberId) {
    Book book = findBookByTitle(bookTitle);
    if (book != null && !book.isBorrowed()) {
        Member member = findMemberById(memberId);
        if (member != null) {
            book.setBorrowed(true);
            return true;
        }
    }
    return false;
}
```

---
# Borrow Book (Controller)

```java
@PostMapping("/book/borrow")
public String borrowBook(@RequestParam String title, @RequestParam int memberId, Model model) {
    boolean success = libraryService.borrowBook(title, memberId);
    if (!success) {
        model.addAttribute("errorMessage", "Failed to borrow the book. Please check the title and member ID.");
        return home(model);
    }
    return "redirect:/library";
}
```

---
# Course Repository Interface

```java
public interface CourseRepository extends JpaRepository<Course, Long> {}
```

---
# Enroll Student in Course

```java
public boolean enrollStudentInCourse(Long studentId, Long courseId) {
    Optional<Student> student = studentRepository.findById(studentId);
    Optional<Course> course = courseRepository.findById(courseId);

    if (student.isPresent() && course.isPresent()) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student.get());
        enrollment.setCourse(course.get());
        enrollmentRepository.save(enrollment);
        return true;
    }
    return false;
}
```

---
#  Enrollment

```java
@KafkaListener(topics = "enroll-student-topic", groupId = "university-group")
public void handleEnrollStudent(EnrollmentRequest enrollmentRequest) {
    Enrollment enrollment = new Enrollment();
    enrollment.setStudentId(enrollmentRequest.getStudentId());
    enrollment.setCourseId(enrollmentRequest.getCourseId());
    enrollmentService.saveEnrollment(enrollment);

    System.out.println("Processed enrollment for student ID " +
        enrollmentRequest.getStudentId() +
        " in course ID " +
        enrollmentRequest.getCourseId());
}
```

---
# Enroll Student

```java
@PayloadRoot(namespace = NAMESPACE_URI, localPart = "EnrollStudentRequest")
@ResponsePayload
public EnrollStudentResponse enrollStudent(@RequestPayload EnrollStudentRequest request) {
    enrollmentService.enrollStudent(request.getStudentId(), request.getCourseId());

    EnrollStudentResponse response = new EnrollStudentResponse();
    response.setStatus("Success");
    return response;
}
```

---
# Create Payment

```java
@PostMapping
public ResponseEntity<Payment> createPayment(@RequestBody PaymentRequest paymentRequest) {
    try {
        Payment payment = paymentService.processPayment(paymentRequest);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
```

---
# Process Payment

```java
public Payment processPayment(Payment payment) {
    if (isDuplicateTransaction(payment)) {
        throw new IllegalArgumentException("Duplicate transaction detected.");
    }

    double transactionFee = calculateTransactionFee(payment.getAmount());

    Payment updatedPayment = new Payment(
        payment.getAmount(),
        payment.getCurrency(),
        payment.getPaymentMethod(),
        "Processed",
        transactionFee
    );
    updatedPayment.setId(payment.getId());

    return paymentRepository.save(updatedPayment);
}
```

----
# Exercise Layered Architecture
> Exercise branch: **5-loop-split**
> Solution branch: **6-separation-of-concerns**

Separate presentation, application, domain, and data concerns.
Move them to folders with respective names.
Introduce application services where appropriate.
 
### Run tests after each reasonably small refactoring step!
