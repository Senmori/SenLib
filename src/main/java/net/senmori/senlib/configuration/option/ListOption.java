package net.senmori.senlib.configuration.option;

import com.google.common.collect.Lists;
import net.senmori.senlib.configuration.ConfigOption;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ListOption<V> extends ConfigOption<List> {
    protected List<V> list;
    protected boolean allowDuplicates = false;
    protected ListOption(String key, List<V> defaultValue) {
        super(key, defaultValue, List.class);
        this.list = defaultValue;
    }

    @Override
    public List<V> getValue() {
        return list;
    }

    @Override
    public boolean parse(String str) {
        return false; // NOOP
    }

    public void setList(List<V> newList) {
        if(newList == null || newList.isEmpty()) {
            this.list = Lists.newArrayList();
        } else {
            this.list = newList;
        }
    }

    public boolean getAllowDuplicates() {
        return allowDuplicates;
    }

    public void setAllowDuplicates(boolean value) {
        this.allowDuplicates = value;
    }

    protected List<V> validateList(List<V> list) {
        if(!getAllowDuplicates()) {
            return list.stream().distinct().collect(Collectors.toList());
        }
        return list;
    }

    protected void validateListInternal() {
        if(!getAllowDuplicates()) {
            this.list = list.stream().distinct().collect(Collectors.toList());
        }
    }
}
