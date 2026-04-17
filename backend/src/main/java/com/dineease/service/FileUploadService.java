package com.dineease.service;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class FileUploadService {
    private static final Logger log = LoggerFactory.getLogger(FileUploadService.class);
    private final Cloudinary cloudinary;
    public FileUploadService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }
    public String uploadFile(MultipartFile file) {
    try {
        String uniqueFilename = UUID.randomUUID().toString();

        @SuppressWarnings("unchecked")
        Map<String, Object> uploadOptions = ObjectUtils.asMap("public_id", "dineease-menu/" + uniqueFilename, "resource_type", "auto" );
        @SuppressWarnings("unchecked")
        Map<String, Object> uploadResult =
        cloudinary.uploader().upload(file.getBytes(), uploadOptions);
        String secureUrl = uploadResult.get("secure_url").toString();
        log.info("Tải ảnh lên thành công: {}", secureUrl);
        return secureUrl;
    } catch (IOException e) {
        log.error("Lỗi tải ảnh lên Cloudinary: {}", e.getMessage());
        throw new RuntimeException("Không thể tải hình ảnh lên hệ thống. Vui lòng thử lại.");
        }
    }
}