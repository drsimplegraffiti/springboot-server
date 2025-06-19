package com.abcode.graphqlspring.pagination;

import lombok.Data;

@Data
public class PaginationRequest {
    private int page = 1;
    private int size = 10;
}