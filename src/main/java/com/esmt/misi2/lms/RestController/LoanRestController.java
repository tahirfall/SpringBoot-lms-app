package com.esmt.misi2.lms.RestController;

import com.esmt.misi2.lms.exceptions.LoanNotFoundException;
import com.esmt.misi2.lms.model.entity.Book;
import com.esmt.misi2.lms.model.entity.Loan;
import com.esmt.misi2.lms.model.entity.UserModel;
import com.esmt.misi2.lms.model.service.ILoanService;
import com.esmt.misi2.lms.model.service.IBookService;
import com.esmt.misi2.lms.model.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Loan> listLoans() {
        return loanService.findAll();
    }

    @GetMapping("/{id}")
    public Loan getLoan(@PathVariable Long id) {
        return loanService.findOne(id);
    }

    @PostMapping("/create-loan")
    public Loan saveLoan(@RequestParam(name = "book", required = false) Long bookId,
                         @RequestParam(name = "user", required = false) Long userId,
                         @Valid @RequestBody Loan loan) {
        List<UserModel> users = userService.findAll();
        List<Book> books = bookService.findAll();

        Book book = bookService.findOne(bookId);
        UserModel user = userService.findOne(userId);

        loan.setBook(book);
        loan.setUser(user);

        loan.setReturned(loan.getReturned());

        return loanService.save(loan);
    }


    @PutMapping("/edit-loan/{id}")
    public Loan editLoan(@PathVariable Long id, @Valid @RequestBody Loan loan) {
        Loan existingLoan = loanService.findOne(id);

        if (existingLoan != null) {
            // Mettre à jour les propriétés du prêt existant avec les nouvelles valeurs
            existingLoan.setBook(loan.getBook());
            existingLoan.setUser(loan.getUser());
            existingLoan.setReturned(loan.getReturned());

            return loanService.save(existingLoan);
        } else {
            // Gérer le cas où le prêt n'est pas trouvé
            throw new LoanNotFoundException("Loan not found with id: " + id);
        }
    }



    @GetMapping("/detail/{id}")
    public Loan detailLoan(@PathVariable Long id) {
        return loanService.findLoanByIdWithBooks(id);
    }

    @DeleteMapping("/delete-loan/{id}")
    public void deleteLoan(@PathVariable Long id) {
        loanService.delete(id);
    }
}

