package cn.wenqi.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wenqi
 */
@Setter
@Getter
@AllArgsConstructor
public class ApiResult {

    private  int code;

    private String msg;

}
