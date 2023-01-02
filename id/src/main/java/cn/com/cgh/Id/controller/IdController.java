package cn.com.cgh.Id.controller;

import cn.com.common.util.R;
import com.baidu.fsg.uid.impl.CachedUidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IdController {
    @Autowired
    private CachedUidGenerator initCachedUidGenerator;

    @RequestMapping(value = "/getId", method = {RequestMethod.GET, RequestMethod.POST})
    public R<Long> getId() {
        return R.success(initCachedUidGenerator.getUID());
    }

    @RequestMapping(value = "/getId/{prefix}", method = {RequestMethod.GET, RequestMethod.POST})
    public R<String> getIdPrefix(@PathVariable("prefix") String prefix) {
        return R.success(prefix + initCachedUidGenerator.getUID());
    }
}
