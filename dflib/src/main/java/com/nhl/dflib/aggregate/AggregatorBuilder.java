package com.nhl.dflib.aggregate;

import com.nhl.dflib.Aggregator;
import com.nhl.dflib.RowPredicate;
import com.nhl.dflib.ValuePredicate;

/**
 * A builder of aggregators that can perform prefiltering and potentially other operations on the DataFrame before
 * applying aggregation function.
 *
 * @since 0.7
 */
public class AggregatorBuilder {

    private RowPredicate rowFilter;

    public AggregatorBuilder filterRows(RowPredicate filter) {
        appendRowFilter(filter);
        return this;
    }

    public <V> AggregatorBuilder filterRows(int columnPos, ValuePredicate<V> filter) {
        appendRowFilter(r -> filter.test((V) r.get(columnPos)));
        return this;
    }

    public <V> AggregatorBuilder filterRows(String columnLabel, ValuePredicate<V> filter) {
        appendRowFilter(r -> filter.test((V) r.get(columnLabel)));
        return this;
    }

    /**
     * Returns the first value in an aggregation range.
     */
    public <T> Aggregator<T> first(String column) {
        return rowFilter != null
                // TODO: once the performance TODO in FilteredAggregator is resolved, perhaps we won't need
                //  a dedicated FilteredFirstAggregator
                ? new FilteredFirstAggregator<>(rowFilter, index -> index.position(column), index -> column)
                : Aggregator.first(column);
    }

    /**
     * Returns the first value in an aggregation range.
     */
    public <T> Aggregator<T> first(int column) {
        return rowFilter != null
                // TODO: once the performance TODO in FilteredAggregator is resolved, perhaps we won't need
                //  a dedicated FilteredFirstAggregator
                ? new FilteredFirstAggregator<>(rowFilter, index -> column, index -> index.getLabel(column))
                : Aggregator.first(column);
    }

    /**
     * Creates an aggregator to count rows
     */
    public Aggregator<Long> countLong() {
        return rowFilter != null
                ? new FilteredAggregator<>(rowFilter, Aggregator.countLong())
                : Aggregator.countLong();
    }

    /**
     * Creates an aggregator to count rows.
     */
    public Aggregator<Integer> countInt() {
        return rowFilter != null
                ? new FilteredAggregator<>(rowFilter, Aggregator.countInt())
                : Aggregator.countInt();
    }

    public Aggregator<Double> averageDouble(String column) {
        return rowFilter != null
                ? new FilteredAggregator<>(rowFilter, Aggregator.averageDouble(column))
                : Aggregator.averageDouble(column);
    }

    public Aggregator<Double> averageDouble(int column) {
        return rowFilter != null
                ? new FilteredAggregator<>(rowFilter, Aggregator.averageDouble(column))
                : Aggregator.averageDouble(column);
    }

    public Aggregator<Long> sumLong(String column) {
        return rowFilter != null
                ? new FilteredAggregator<>(rowFilter, Aggregator.sumLong(column))
                : Aggregator.sumLong(column);
    }

    public Aggregator<Long> sumLong(int column) {
        return rowFilter != null
                ? new FilteredAggregator<>(rowFilter, Aggregator.sumLong(column))
                : Aggregator.sumLong(column);
    }

    public Aggregator<Integer> sumInt(String column) {
        return rowFilter != null
                ? new FilteredAggregator<>(rowFilter, Aggregator.sumInt(column))
                : Aggregator.sumInt(column);
    }

    public Aggregator<Integer> sumInt(int column) {
        return rowFilter != null
                ? new FilteredAggregator<>(rowFilter, Aggregator.sumInt(column))
                : Aggregator.sumInt(column);
    }

    public Aggregator<Double> sumDouble(String column) {
        return rowFilter != null
                ? new FilteredAggregator<>(rowFilter, Aggregator.sumDouble(column))
                : Aggregator.sumDouble(column);
    }

    public Aggregator<Double> sumDouble(int column) {
        return rowFilter != null
                ? new FilteredAggregator<>(rowFilter, Aggregator.sumDouble(column))
                : Aggregator.sumDouble(column);
    }

    public <T extends Comparable<T>> Aggregator<T> max(String column) {
        return rowFilter != null
                ? new FilteredAggregator<>(rowFilter, Aggregator.<T>max(column))
                : Aggregator.max(column);
    }

    public <T extends Comparable<T>> Aggregator<T> max(int column) {
        return rowFilter != null
                ? new FilteredAggregator<>(rowFilter, Aggregator.<T>max(column))
                : Aggregator.max(column);
    }

    public <T extends Comparable<T>> Aggregator<T> min(String column) {
        return rowFilter != null
                ? new FilteredAggregator<>(rowFilter, Aggregator.<T>min(column))
                : Aggregator.max(column);
    }

    public <T extends Comparable<T>> Aggregator<T> min(int column) {
        return rowFilter != null
                ? new FilteredAggregator<>(rowFilter, Aggregator.<T>min(column))
                : Aggregator.max(column);
    }

    protected void appendRowFilter(RowPredicate filter) {
        this.rowFilter = this.rowFilter != null ? this.rowFilter.and(filter) : filter;
    }
}
