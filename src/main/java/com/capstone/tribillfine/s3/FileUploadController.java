package com.capstone.tribillfine.s3;

import com.capstone.tribillfine.domain.account.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class FileUploadController {

    private final S3Upload s3Upload;
    private final BudgetRepository budgetRepository;

    @PostMapping("/upload")
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        return s3Upload.upload(multipartFile);
    }
}