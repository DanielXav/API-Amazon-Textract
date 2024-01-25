package com.danielxavier.demo.services;

import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.model.Block;
import com.amazonaws.services.textract.model.DetectDocumentTextRequest;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.amazonaws.services.textract.model.Document;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.util.Base64;

@Service
public class TextractService {

    private final AmazonTextract textractClient;

    public TextractService(AmazonTextract textractClient) {
        this.textractClient = textractClient;
    }

    public String analyzeDocument(String jsonBase64) {
        JSONObject jsonObject = new JSONObject(jsonBase64);
        String base64Image = jsonObject.getString("image");

        ByteBuffer imageBytes = ByteBuffer.wrap(Base64.getDecoder().decode(base64Image));
        Document document = new Document().withBytes(imageBytes);

        DetectDocumentTextRequest request = new DetectDocumentTextRequest().withDocument(document);
        DetectDocumentTextResult result = textractClient.detectDocumentText(request);

        StringBuilder extractedText = new StringBuilder();
        for (Block block : result.getBlocks()) {
            if ("LINE".equals(block.getBlockType())) {
                extractedText.append(block.getText()).append("\n");
            }
        }

        return extractedText.toString();
    }

}
