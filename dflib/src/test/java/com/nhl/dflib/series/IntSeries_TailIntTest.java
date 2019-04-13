package com.nhl.dflib.series;

import com.nhl.dflib.unit.IntSeriesAsserts;
import org.junit.Test;

public class IntSeries_TailIntTest {

    @Test
    public void test() {
        IntSeries s = new IntSeries(3, 4, 2).tailInt(2);
        new IntSeriesAsserts(s).expectData(4, 2);
    }

    @Test
    public void test_Zero() {
        IntSeries s = new IntSeries(3, 4, 2).tailInt(0);
        new IntSeriesAsserts(s).expectData();
    }

    @Test
    public void test_OutOfBounds() {
        IntSeries s = new IntSeries(3, 4, 2).tailInt(4);
        new IntSeriesAsserts(s).expectData(3, 4, 2);
    }
}