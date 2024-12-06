package com.oniesoft.dto;

public class ApiResponse<T> {
    private T data;
    private PaginationMetadata pagination;

    public ApiResponse() {}

    public ApiResponse(T data, PaginationMetadata pagination) {
        this.data = data;
        this.pagination = pagination;
    }

    public static <T> ApiResponse<T> success(T data, PaginationMetadata pagination) {
        return new ApiResponse<>(data, pagination);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public PaginationMetadata getPagination() {
        return pagination;
    }

    public void setPagination(PaginationMetadata pagination) {
        this.pagination = pagination;
    }

    // Inner class for pagination metadata
    public static class PaginationMetadata {
        private int currentPage;
        private int totalPages;
        private long totalElements;
        private int pageSize;

        public PaginationMetadata(int currentPage, int totalPages, long totalElements, int pageSize) {
            this.currentPage = currentPage;
            this.totalPages = totalPages;
            this.totalElements = totalElements;
            this.pageSize = pageSize;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(long totalElements) {
            this.totalElements = totalElements;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }
}

