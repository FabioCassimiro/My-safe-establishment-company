package br.com.mysafeestablishmentcompany.api.imgur;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class ImgurClientIdRequestInterceptor implements RequestInterceptor {

    private String clientID = "460dbaf6923bd2d";

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String CLIENT_ID = "Client-ID";

    public ImgurClientIdRequestInterceptor() {
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", CLIENT_ID, this.clientID));
    }
}