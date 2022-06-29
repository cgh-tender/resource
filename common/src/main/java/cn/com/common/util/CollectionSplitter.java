package cn.com.common.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author haider
 * @date 2022年06月29日 18:09
 */
public class CollectionSplitter<T> implements Iterator<Collection<T>> {
    private int SIZE_LIMIT = 1;
    private final List<T> collections;
    private int currIndex = 0;

    public void setLimit(int limit) {
        this.SIZE_LIMIT = limit;
    }

    public CollectionSplitter(List<T> collections) {
        this.collections = collections;
    }

    @Override
    public boolean hasNext() {
        return currIndex < collections.size();
    }

    @Override public List<T> next() {
        int startIndex = getStartIndex();
        int nextIndex = calcMessageSize();
        List<T> subList = collections.subList(startIndex, nextIndex);
        currIndex = nextIndex;
        return subList;
    }
    private int getStartIndex() {
        return currIndex;
    }
    private int calcMessageSize() {
        int tmpSize = getStartIndex() + SIZE_LIMIT;
        if (tmpSize>collections.size()){
            tmpSize = collections.size();
        }
        return tmpSize;
    }
}
