package br.com.mysafeestablishmentcompany.api.imgur;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class ImgurBearerRequestInterceptor implements RequestInterceptor {

    private static final String IMGUR_TOKEN = "41bcb6a79d204bd60e1ae0d5d192d868b3fff0fc";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_TYPE = "Bearer";

    public ImgurBearerRequestInterceptor() {
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, IMGUR_TOKEN));
    }
}