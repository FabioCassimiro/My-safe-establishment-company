package br.com.mysafeestablishmentcompany.api.response;

import br.com.mysafeestablishmentcompany.domain.OrderPad;

public class CloseOrderPadResponse extends ErrorResponse{

    private OrderPad orderPad;

    public CloseOrderPadResponse(OrderPad orderPad) {
        this.orderPad = orderPad;
    }

    public CloseOrderPadResponse(String errorMessage) {
        super(errorMessage);
        this.orderPad = null;
    }

    public OrderPad getOrderPad() {
        return orderPad;
    }

    public void setOrderPad(OrderPad orderPad) {
        this.orderPad = orderPad;
    }

    @Override
    public String toString() {
        return "CloseOrderPadResponse{" +
                "orderPad=" + orderPad +
                '}';
    }
}
