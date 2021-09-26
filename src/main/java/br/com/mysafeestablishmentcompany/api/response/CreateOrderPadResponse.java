package br.com.mysafeestablishmentcompany.api.response;

import br.com.mysafeestablishmentcompany.domain.OrderPad;

public class CreateOrderPadResponse extends ErrorResponse{

    private OrderPad orderPad;

    public CreateOrderPadResponse(OrderPad orderPad) {
        this.orderPad = orderPad;
    }

    public CreateOrderPadResponse(String errorMessage) {
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
        return "CreateOrderPadResponse{" +
                "orderPad=" + orderPad +
                '}';
    }
}
