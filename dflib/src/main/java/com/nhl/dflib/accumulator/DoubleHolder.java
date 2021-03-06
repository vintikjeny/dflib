package com.nhl.dflib.accumulator;

/**
 * @since 0.8
 */
public class DoubleHolder implements ValueHolder<Double> {

    private double v;

    @Override
    public Double get() {
        return v;
    }

    @Override
    public void set(Double v) {
        this.v = v != null ? v : 0.;
    }

    @Override
    public void store(Accumulator<Double> accumulator) {
        accumulator.addDouble(v);
    }

    @Override
    public void store(int pos, Accumulator<Double> accumulator) {
        accumulator.setDouble(pos, v);
    }

    @Override
    public double getDouble() {
        return v;
    }

    @Override
    public void setDouble(double v) {
        this.v = v;
    }
}
