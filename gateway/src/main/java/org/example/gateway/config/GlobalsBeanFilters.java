package org.example.gateway.config;

import org.example.gateway.filters.AuthGlobalFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;

public class GlobalsBeanFilters {

    public GlobalFilter AuthFilter(){
        return new AuthGlobalFilter();
    }
}
