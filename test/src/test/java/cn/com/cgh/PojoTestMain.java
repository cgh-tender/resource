package cn.com.cgh;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class PojoTestMain {
    public static void main(String[] args) {
        List<PojoTest> resource = new ArrayList<>();
        Map<String, PojoTest> c = new HashMap<>();
        for (PojoTest test : resource) {
            if (StringUtils.isBlank(test.getCode()) || StringUtils.isBlank(test.getParentCode())) {
                System.out.println("数据异常 " + test);
                continue;
            }
            c.put(test.getCode(), test);
        }
        for (PojoTest test : resource) {
            if (StringUtils.isBlank(test.getCode()) || StringUtils.isBlank(test.getParentCode())) {
                continue;
            }
            PojoTest c1 = c.get(test.getCode());
            PojoTest p1 = c.get(test.getParentCode());
            if (p1 != null) {
                c1.setParent(p1);
                p1.add(c1);
            }
        }

        Map<String, Set<String>> child = new HashMap<>();
        Map<String, Set<String>> parent = new HashMap<>();
        for (PojoTest test : resource) {
            if (StringUtils.isBlank(test.getCode()) || StringUtils.isBlank(test.getParentCode())) {
                continue;
            }
            if (child.get(test.getParentCode()) == null) {
                Set<String> set = new HashSet<>();
                set.add(test.getCode());
                set.add(test.getParentCode());
                child.put(test.getParentCode(), set);

                set = new HashSet<>();
                set.add(test.getCode());
                child.put(test.getCode(), set);
            } else {
                Set<String> set = new HashSet<>();
                set.add(test.getCode());
                child.put(test.getCode(), set);
                child.get(test.getParentCode()).add(test.getCode());
            }
            if (parent.get(test.getCode()) == null) {
                Set<String> set = new HashSet<>();
                set.add(test.getParentCode());
                parent.put(test.getCode(), set);
            } else {
                parent.get(test.getCode()).add(test.getParentCode());
            }
        }
        final List<String> saveed = new ArrayList<>();
        child.forEach((k, v) -> {
            Set<String> strings = parserChiled(child, k, v, saveed);
            if (strings != null) {
                v.addAll(strings);
            }
        });
        final List<String> saveed1 = new ArrayList<>();
        parent.forEach((k, v) -> {
            Set<String> strings = parserParent(parent, k, v, saveed1);
            if (strings != null) {
                v.addAll(strings);
            }
        });
        System.out.println(child.size());
        System.out.println(parent.size());
        System.out.println(resource.size());
        System.out.println();
        System.out.println(resource.size() - parent.size() + "两条数据异常");
        System.out.println(child.size() - parent.size() + "两个截断父节点");
    }

    private static Set<String> parserParent(Map<String, Set<String>> parent, String k, Set<String> v, List<String> saveed) {
        if (v == null) return null;
        if (saveed.contains(k)) return null;
        saveed.add(k);
        Set<String> resource = new HashSet<>(v);
        for (String sv : v) {
            Set<String> set = parent.get(sv);
            if (set != null) {
                Set<String> allParent = parserParent(parent, sv, set, saveed);
                if (allParent != null) {
                    set.addAll(allParent);
                }
                resource.addAll(set);
            }
        }
        return resource;
    }

    private static Set<String> parserChiled(Map<String, Set<String>> child, String k, Set<String> v, List<String> saveed) {
        if (saveed.contains(k)) return null;
        saveed.add(k);
        Set<String> resource = new HashSet<>(v);
        for (String sv : v) {
            if (!StringUtils.equals(sv, k)) {
                Set<String> set = child.get(sv);
                Set<String> allChiled = parserChiled(child, sv, set, saveed);
                if (allChiled != null) {
                    set.addAll(allChiled);
                }
                resource.addAll(set);
            }
        }
        return resource;
    }
}
