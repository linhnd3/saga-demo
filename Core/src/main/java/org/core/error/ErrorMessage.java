package org.core.error;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
@Getter
@ToString
public enum ErrorMessage {
    SUCCESS("success"),
    FAILED("failed"),
    SAVE_ORDER_FAILED("Cannot save order"),
    SAVE_DELIVERY_FAILED("Cannot save delivery"),
    SAVE_PAYMENT_FAILED("Cannot save payment"),
    SEND_RESTAURANT_FAILED("Cannot send restaurant"),
    CHARGE_PAYMENT_FAILED("Charge payment failed"),
    UPDATE_BALANCE_FAILED("Update balance failed"),
    NOT_ENOUGH_MONEY("Wallet doesn't have enough money"),
    SEND_PAYMENT_FAILED("Send payment failed"),
    OUT_OF_GRADIENT("Restaurant is out of food");

    ErrorMessage(String message) {
        this.message = message;
    }

    private String message;


}
