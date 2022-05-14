package edu.iis.mto.coffee.machine;

import edu.iis.mto.coffee.Status;

public class Coffee {

    private Status status;
    private int waterAmount;
    private int milkAmout;
    private String message;

    public int getWaterAmount() {
        return waterAmount;
    }

    void setWaterAmount(int waterAmount) {
        this.waterAmount = waterAmount;
    }

    public int getMilkAmout() {
        return milkAmout;
    }

    void setMilkAmout(int milkAmout) {
        this.milkAmout = milkAmout;
    }

    public Status getStatus() {
        return status;
    }

    void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }

}
