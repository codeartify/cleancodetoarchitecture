# Can we always just move logic to other classes (Feature Envy)?

----
# What about different concerns? 

----
# Layered Architecture (DDD style)
* Presentation Layer
* Business Layer
  * Sometimes, the business layer additionally gets divided into application and domain layers.
* Data Access Layer

----
# Domain Layer
* Core business logic
* Often company-wide business rules that could be reused in multiple applications (e.g. "PhoneNumber", "Email")

----
# Application Layer
* Coordinates the "dance of domain objects"
* Fetches data from the data access, executes business logic through domain objects, and updates the data access

----
# Presentation Layer
* Receives (and validates) user input
* Calls business logic through "application services"
* Presents results to the user

----
# Data access layer
* Handles data access (DB, file system, REST APIs, etc.)

----
# Exercise - Concern Separation

----

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

```java
public boolean borrowBook(String bookTitle, String memberId) {
    Book book = booksRepository.findBookByTitle(bookTitle);
    if (book != null && !book.isBorrowed()) {
        Member member = memberRepository.findMemberById(memberId);
        if (member != null) {
            book.setBorrowed(true);
            return true;
        }
    }
    return false;
}
```

---

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

```java
public interface CourseRepository extends JpaRepository<Course, Long> {}
```

---

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

```java
public Payment createPayment(Payment payment) {
    ResponseEntity<Payment> response =
            restTemplate.postForEntity(paymentServiceUrl + "/payments", payment, Payment.class);
    return response.getBody();
}

```

---

```java
public void sendPayment(Payment payment) {
    PaymentDTO paymentDTO = new PaymentDTO(
            payment.getId(),
            payment.getAmount(),
            payment.getCurrency(),
            payment.getPaymentMethod(),
            payment.getStatus(),
            payment.getTransactionFee()
    );
    
    kafkaTemplate.send(PAYMENT_TOPIC, paymentDTO);
}
```

---

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
---

```java
private void validate() {
    if (amount <= 0) {
        throw new IllegalArgumentException("Payment amount must be greater than zero.");
    }
    if (currency == null || currency.isEmpty()) {
        throw new IllegalArgumentException("Currency cannot be null or empty.");
    }
    if (paymentMethod == null || paymentMethod.isEmpty()) {
        throw new IllegalArgumentException("Payment method cannot be null or empty.");
    }
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
