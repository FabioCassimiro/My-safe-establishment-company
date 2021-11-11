package br.com.mysafeestablishmentcompany.api.imgur;

import feign.Feign;
import feign.form.spring.SpringFormEncoder;
import feign.jackson.JacksonDecoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.web.multipart.MultipartFile;

public class ImgurClient implements ImgurAPI{

    public Feign.Builder getBuilder() {
        return Feign.builder()
                .requestInterceptor(new ImgurRequestInterceptor())
                .encoder(new SpringFormEncoder())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(ImgurAPI.class));

    }

    private ImgurAPI getApi() {
        return this.getBuilder().target(ImgurAPI.class, "https://api.imgur.com/3");
    }

    @Override
    public UploadImageResponse uploadImage(MultipartFile file) {
        return getApi().uploadImage(file);
    }
}
