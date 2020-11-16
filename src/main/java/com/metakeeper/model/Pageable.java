package com.metakeeper.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.QueryValue;

import javax.annotation.Nullable;
import java.util.StringJoiner;

/**
 * Description
 *
 * @author Gromov Vitaly.
 * Created 05.11.2020
 */
@Introspected
public class Pageable {
    @Nullable
    @QueryValue
    private int page;
    @Nullable
    @QueryValue
    private int size;

    @Nullable
    @QueryValue
    private String sort;

    @Nullable
    @QueryValue
    private String query;

    public Pageable() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Nullable
    public String getSort() {
        return sort;
    }

    public void setSort(@Nullable String sort) {
        this.sort = sort;
    }

    @Nullable
    public String getQuery() {
        return query;
    }

    public void setQuery(@Nullable String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Pageable.class.getSimpleName() + "[", "]")
                .add("page=" + page)
                .add("size=" + size)
                .add("sort='" + sort + "'")
                .add("query='" + query + "'")
                .toString();
    }
}
