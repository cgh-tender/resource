package cn.com.cgh.boot.controller;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author haider
 * @date 2021年09月24日 11:31
 */
@Data
public class D {
    /**
     * name
     */
    @NotNull
    private String nameD;
    /**
     * age
     */
    private int ageD;
}
