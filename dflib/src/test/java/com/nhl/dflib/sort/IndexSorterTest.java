package com.nhl.dflib.sort;

import com.nhl.dflib.DataFrame;
import com.nhl.dflib.IntSeries;
import com.nhl.dflib.unit.IntSeriesAsserts;
import org.junit.Test;

public class IndexSorterTest {

    @Test
    public void testSortIndex() {
        DataFrame df = DataFrame.newFrame("a", "b").foldByRow(
                1, "x",
                2, "y",
                1, "z",
                0, "a",
                1, "x");

        IntSeries sortIndex = new IndexSorter(df).sortIndex(Sorters.sorter(1, true));
        new IntSeriesAsserts(sortIndex).expectData(3, 0, 4, 1, 2);
    }
}
