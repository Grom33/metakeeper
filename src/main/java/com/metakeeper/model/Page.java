package com.metakeeper.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Collections;
import java.util.List;

/**
 * Description
 *
 * @author Gromov Vitaly.
 * Created 05.11.2020
 */
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Page<T> {
    private static final Page EMPTY = new Page();

    public static final int PAGE_DEFAULT = 1;
    public static final int PER_PAGE_DEFAULT = 10;

    private String query;
    private String sort;

    private Long totalRows = 0L;
    private Integer page = 1;

    private Integer perPage = PER_PAGE_DEFAULT;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> records = Collections.emptyList();
}
