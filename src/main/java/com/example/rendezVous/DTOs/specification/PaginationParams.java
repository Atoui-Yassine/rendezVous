package com.example.rendezVous.DTOs.specification;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PaginationParams {
    private int pageNumber=0;
    private int pageSize=20;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        if (pageNumber > 0 )
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize >1 && pageSize < 20)
        this.pageSize = pageSize;
    }
}
