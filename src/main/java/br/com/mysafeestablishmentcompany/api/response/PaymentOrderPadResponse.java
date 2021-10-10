package br.com.mysafeestablishmentcompany.api.response;

import br.com.mysafeestablishmentcompany.domain.OrderPad;

public class PaymentOrderPadResponse extends ErrorResponse{

    private OrderPad orderPad;

    public OrderPad getOrderPad() {
        return orderPad;
    }

    public void setOrderPad(OrderPad orderPad) {
        this.orderPad = orderPad;
    }

    public PaymentOrderPadResponse(OrderPad orderPad) {
        this.orderPad = orderPad;
    }

    public PaymentOrderPadResponse(String errorMessage) {
        super(errorMessage);
        this.orderPad = null;
    }
}
