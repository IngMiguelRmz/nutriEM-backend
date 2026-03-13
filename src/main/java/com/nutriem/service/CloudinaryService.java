package com.nutriem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.*;
import java.net.http.*;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CloudinaryService {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    public String uploadImage(MultipartFile file) throws Exception {
        // Build multipart body manually (no SDK, just java.net.http)
        String boundary = "----NutriEMBoundary" + System.currentTimeMillis();

        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String folder    = "nutriem/meal-ingredients";

        // Signature: sign "folder=...&timestamp=...&upload_preset=" + secret
        // Simple unsigned upload via upload_preset or signed:
        String toSign   = "folder=" + folder + "&timestamp=" + timestamp + apiSecret;
        String signature = sha1Hex(toSign);

        byte[] fileBytes = file.getBytes();
        String fileName  = file.getOriginalFilename() != null ? file.getOriginalFilename() : "image.jpg";

        // Build multipart body
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(baos, "UTF-8"), true);
        addField(pw, baos, boundary, "api_key",   apiKey);
        addField(pw, baos, boundary, "timestamp", timestamp);
        addField(pw, baos, boundary, "folder",    folder);
        addField(pw, baos, boundary, "signature", signature);
        // File part
        pw.append("--").append(boundary).append("\r\n");
        pw.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(fileName).append("\"").append("\r\n");
        pw.append("Content-Type: ").append(file.getContentType() != null ? file.getContentType() : "image/jpeg").append("\r\n");
        pw.append("\r\n");
        pw.flush();
        baos.write(fileBytes);
        baos.flush();
        pw.append("\r\n");
        pw.append("--").append(boundary).append("--").append("\r\n");
        pw.flush();

        byte[] body = baos.toByteArray();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.cloudinary.com/v1_1/" + cloudName + "/image/upload"))
            .header("Content-Type", "multipart/form-data; boundary=" + boundary)
            .POST(HttpRequest.BodyPublishers.ofByteArray(body))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Cloudinary upload failed: " + response.body());
        }

        // Parse secure_url from JSON response (no Jackson needed — simple regex)
        Matcher m = Pattern.compile("\"secure_url\"\\s*:\\s*\"([^\"]+)\"").matcher(response.body());
        if (!m.find()) throw new RuntimeException("Could not parse Cloudinary response");
        return m.group(1).replace("\\/", "/");
    }

    private void addField(PrintWriter pw, OutputStream os, String boundary, String name, String value) throws IOException {
        pw.append("--").append(boundary).append("\r\n");
        pw.append("Content-Disposition: form-data; name=\"").append(name).append("\"").append("\r\n");
        pw.append("\r\n").append(value).append("\r\n");
        pw.flush();
    }

    private String sha1Hex(String input) throws Exception {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-1");
        byte[] result = md.digest(input.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : result) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
