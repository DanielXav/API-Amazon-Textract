package com.danielxavier.demo.controller;

import com.danielxavier.demo.services.TextractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;

@RestController
@RequestMapping("/textract")
public class TextractController {

    @Autowired
    private TextractService textractService;

    public TextractController(TextractService textractService) {
        this.textractService = textractService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<?> analyzeDocument(@RequestBody String base64Image) {
        try {
            String result = textractService.analyzeDocument(base64Image);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
