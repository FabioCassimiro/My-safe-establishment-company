package br.com.mysafeestablishmentcompany.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompanyUtils {

    public static final double TAX_RATE = 0.10;

    public static final String ORDERPAD_STATUS_OPEN = "0";
    public static final String ORDERPAD_STATUS_AWAITING_PAYMENT = "1";
    public static final String ORDERPAD_STATUS_PAID = "2";
    public static final String ORDERPAD_STATUS_AWAITING_MANUAL_PAYMENT = "3";

    public static final String ORDER_STATUS_IN_PROGRESS = "0";
    public static final String ORDER_STATUS_ACCEPTED = "1";
    public static final String ORDER_STATUS_IN_DELIVERY = "2";
    public static final String ORDER_STATUS_DELIVERED = "3";
    public static final List<String> ordersStatus = Arrays.asList(
            ORDER_STATUS_IN_PROGRESS,
            ORDER_STATUS_ACCEPTED,
            ORDER_STATUS_IN_DELIVERY,
            ORDER_STATUS_DELIVERED
    );

    public static final String CARD_CREDIT_PAYMENT = "CREDITO";
    public static final String CARD_DEBT_PAYMENT = "DEBITO";
    public static final String MONEY_PAYMENT = "DINHEIRO";

    public static final String TABLE_STATUS_AVALIABLE = "0";
    public static final String TABLE_STATUS_NOT_AVAILABLE = "1";

}
