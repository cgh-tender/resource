package cn.com.cgh.boot.controller;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author haider
 * @date 2021年09月24日 11:31
 */
@Data
public class Names {
    /**
     * name
     */
    @NotNull
    private String name;
    /**
     * age
     */
    private int age;
    /**
     * Save
     */
    @Valid
    private List<D> names;
}
