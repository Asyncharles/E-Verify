package net.everify.utils;

public class Pair<R, L> {

    private R rValue;
    private L lValue;


    public Pair(R rValue, L lValue) {
        this.rValue = rValue;
        this.lValue = lValue;
    }

    public static <R, L> Pair<R, L> of(R rightValue, L leftValue) {
        return new Pair<>(rightValue, leftValue);
    }

    public R getRight() {
        return rValue;
    }

    public L getLeft() {
        return lValue;
    }

}
