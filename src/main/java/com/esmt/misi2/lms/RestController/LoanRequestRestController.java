package com.esmt.misi2.lms.RestController;


import com.esmt.misi2.lms.exceptions.BookNotFoundException;
import com.esmt.misi2.lms.exceptions.LoanNotFoundException;
import com.esmt.misi2.lms.exceptions.LoanRequestNotFoundException;
import com.esmt.misi2.lms.exceptions.UserNotFoundException;
import com.esmt.misi2.lms.model.entity.LoanRequest;
import com.esmt.misi2.lms.model.service.ILoanRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loanrequests")
public class LoanRequestRestController {

    @Autowired
    private ILoanRequestService loanRequestService;

    @GetMapping("/list-requests")
    public ResponseEntity<List<LoanRequest>> listLoanRequests() {
        List<LoanRequest> loanRequests = loanRequestService.findAll();
        return ResponseEntity.ok(loanRequests);
    }

    @GetMapping("/request/{id}")
    public ResponseEntity<LoanRequest> getLoanRequest(@PathVariable Long id) {
        LoanRequest loanRequest = loanRequestService.findOne(id);
        if (loanRequest != null) {
            return ResponseEntity.ok(loanRequest);
        } else {
            throw new LoanRequestNotFoundException("Loan request with id: " + id + " not found");
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<LoanRequest> submitLoanRequest(@Valid @RequestBody LoanRequest loanRequest) {
        LoanRequest savedLoanRequest = loanRequestService.save(loanRequest);
        return ResponseEntity.ok(savedLoanRequest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LoanRequest> updateLoanRequest(@PathVariable Long id, @Valid @RequestBody LoanRequest loanRequest) {
        LoanRequest existingLoanRequest = loanRequestService.findOne(id);
        if (existingLoanRequest != null) {
            existingLoanRequest.setStatus(loanRequest.getStatus());
            LoanRequest updatedLoanRequest = loanRequestService.save(existingLoanRequest);
            return ResponseEntity.ok(updatedLoanRequest);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelLoanRequest(@PathVariable Long id) {
        loanRequestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
