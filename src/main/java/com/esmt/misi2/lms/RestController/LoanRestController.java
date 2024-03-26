package com.esmt.misi2.lms.RestController;

import com.esmt.misi2.lms.exceptions.LoanNotFoundException;
import com.esmt.misi2.lms.exceptions.UserNotFoundException;
import com.esmt.misi2.lms.model.entity.Book;
import com.esmt.misi2.lms.model.entity.Loan;
import com.esmt.misi2.lms.model.entity.UserModel;
import com.esmt.misi2.lms.model.service.ILoanService;
import com.esmt.misi2.lms.model.service.IBookService;
import com.esmt.misi2.lms.model.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanRestController {

    @Autowired
    private ILoanService loanService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IBookService bookService;

    @GetMapping("/list-loans")
    public ResponseEntity<List<Loan>> listLoans() {
        List<Loan> loans = loanService.findAll();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/loan/{id}")
    public ResponseEntity<Loan> getLoan(@PathVariable Long id) {
        Loan loan = loanService.findOne(id);
        if (loan != null) {
            return ResponseEntity.ok(loan);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create-loan")
    public ResponseEntity<Loan> saveLoan(@RequestParam(name = "book", required = false) Long bookId,
                                         @RequestParam(name = "user", required = false) Long userId,
                                         @Valid @RequestBody Loan loan) {
        Book book = bookService.findOne(bookId);
        UserModel user = userService.findOne(userId);

        loan.setBook(book);
        loan.setUser(user);

        Loan savedLoan = loanService.save(loan);
        return ResponseEntity.ok(savedLoan);
    }

    @PutMapping("/edit-loan/{id}")
    public ResponseEntity<Loan> editLoan(@PathVariable Long id, @Valid @RequestBody Loan loan) {
        Loan existingLoan = loanService.findOne(id);
        if (existingLoan != null) {
            existingLoan.setBook(loan.getBook());
            existingLoan.setUser(loan.getUser());
            existingLoan.setReturned(loan.getReturned());

            Loan updatedLoan = loanService.save(existingLoan);
            return ResponseEntity.ok(updatedLoan);
        } else {
            throw new LoanNotFoundException("Loan with id: " + id + " not found");
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Loan> detailLoan(@PathVariable Long id) {
        Loan loan = loanService.findLoanByIdWithBooks(id);
        if (loan != null) {
            return ResponseEntity.ok(loan);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete-loan/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
