package br.com.mysafeestablishmentcompany.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyEstablishmentUtils {

    public static void validateCardNumber(String cardNumber) throws Exception {
        Pattern patternCardNumer = Pattern.compile("^[0-9]{15}(?:[0-9]{1})?$");
        Matcher matcher = patternCardNumer.matcher(cardNumber);
        if (!matcher.find()) {
            throw new Exception("Numero de cartão informado é invalido");
        }
    }

}
