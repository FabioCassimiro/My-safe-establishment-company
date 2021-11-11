package br.com.mysafeestablishmentcompany.api.imgur;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.multipart.MultipartFile;

public interface ImgurAPI {

    @RequestLine("POST /upload")
    @Headers("Content-Type: multipart/form-data")
    UploadImageResponse uploadImage(@Param("file") MultipartFile file);
}
