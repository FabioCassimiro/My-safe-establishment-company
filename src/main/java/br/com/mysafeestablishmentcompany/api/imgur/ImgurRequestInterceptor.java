package br.com.mysafeestablishmentcompany.api.imgur;

import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;

public class ImgurRequestInterceptor implements feign.RequestInterceptor {

    @Value("${imgurToken}")
    private  String imgurToken;
    @Value("${clientID}")
    private  String clientID;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_TYPE = "Bearer";
    private static final String CLIENT_ID = "Client-ID";

    public ImgurRequestInterceptor() {

    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, this.imgurToken));
        requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", CLIENT_ID, this.clientID));
    }
}
