package com.forexcard.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.forexcard.dto.UserDetailsDTO;
import com.forexcard.model.User;
import com.forexcard.service.ApplyCardService;

@RestController
@RequestMapping("/apply")
public class ApplyCardController {

    @Autowired
    private ApplyCardService applyCardService;

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> applyForexCard(
            @PathVariable("id") Integer id,
            @RequestPart("userDetails") UserDetailsDTO userDetails,
            @RequestPart("salarySlip") MultipartFile salarySlip) {
        try {
            applyCardService.processCardApplication(id, userDetails, salarySlip);
            return ResponseEntity.ok("Application submitted successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error processing uploaded document");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(ex.getMessage());
        }
    }

    @GetMapping("/document/{id}")
    public ResponseEntity<byte[]> getSalarySlip(@PathVariable("id") Integer id) {
        byte[] document = applyCardService.getSalarySlipByUserId(id);
        if (document == null || document.length == 0) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("salary_slip.pdf").build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(document);
    }

}
