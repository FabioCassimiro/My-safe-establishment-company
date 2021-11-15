package br.com.mysafeestablishmentcompany.api.imgur;

import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class ImgurClient implements ImgurAPI {

    private static final String IMGUR_URL = "https://api.imgur.com/3";
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ImgurClient.class);

    private static class CustomerLogger extends Logger {
        @Override
        protected void log(String s, String s1, Object... objects) {
            logger.info(String.format(s.concat(" - ") + s1 + "%n", objects));
        }
    }

    public Feign.Builder getBuilder() {
        return Feign.builder()
                .encoder(new FormEncoder(new JacksonEncoder()))
                .decoder(new JacksonDecoder())
                .retryer(Retryer.NEVER_RETRY)
                .options(new Request.Options(60, TimeUnit.SECONDS,
                        60, TimeUnit.SECONDS,
                        true))
                .logLevel(Logger.Level.FULL)
                .logger(new CustomerLogger());
    }

    private ImgurAPI getBearerApi() {
        return this.getBuilder()
                .requestInterceptor(new ImgurClientIdRequestInterceptor())
                .target(ImgurAPI.class, IMGUR_URL);
    }

    private ImgurAPI getClientIdApi() {
        return this.getBuilder()
                .requestInterceptor(new ImgurClientIdRequestInterceptor())
                .target(ImgurAPI.class, IMGUR_URL);
    }

    @Override
    public UploadImageResponse uploadImage(File image) {
        return getBearerApi().uploadImage(image);
    }

    @Override
    public void deleteImage(String deleteHash) {
        getClientIdApi().deleteImage(deleteHash);
    }

}