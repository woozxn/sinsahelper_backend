package com.ujm.sinsahelper.common.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ApiResponse<T> {

    private Map<String,Object> meta = new HashMap<>();
    private List<T> data;

    public ApiResponse() {
        this.meta.put(Constants.Key.CODE.getKey(), Constants.Code.SUCCESS.getCode());
        this.meta.put(Constants.Key.MESSAGE.getKey(), Constants.Code.SUCCESS.getMessage());
        this.data = Collections.emptyList();
    }

}
