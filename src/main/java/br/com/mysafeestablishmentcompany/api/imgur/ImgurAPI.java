package br.com.mysafeestablishmentcompany.api.imgur;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.io.File;

public interface ImgurAPI {

    @RequestLine("POST /upload")
    @Headers({"Content-Type: multipart/form-data", "Accept-Encoding: identity"})
    UploadImageResponse uploadImage(@Param("image") File image);

    @RequestLine("DELETE /image/{deleteHash}")
    void deleteImage(@Param("deleteHash") String deleteHash);
}
