package com.redvinca.assignment.ecom_backend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.redvinca.assignment.ecom_backend.serviceimpl.PdfGeneratorService;
import com.redvinca.assignment.ecom_backend.model.Cart;

@RestController
public class PdfGeneratorController {

    @Autowired
    private PdfGeneratorService pdfService;

    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestBody List<Cart> selectedItems) {
        try {
            byte[] pdfBytes = pdfService.generatePdf(selectedItems);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=generated.pdf");
            headers.add("Content-Type", "application/pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
