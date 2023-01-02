package cn.com.cgh;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class PojoTest {
    private String code;
    private String parentCode;

    private List<PojoTest> chiled = new ArrayList<>();
    public void add(PojoTest p){
        chiled.add(p);
    }
    private PojoTest parent;
}
