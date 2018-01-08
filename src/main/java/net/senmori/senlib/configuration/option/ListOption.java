package net.senmori.senlib.configuration.option;

import com.google.common.collect.Lists;
import net.senmori.senlib.configuration.ConfigOption;

import java.util.List;

public abstract class ListOption<V> extends ConfigOption<List> {
    private List<V> list;
    protected ListOption(String key, List<V> defaultValue) {
        super(key, defaultValue, List.class);
        this.list = defaultValue;
    }

    @Override
    public List<V> getValue() {
        return list;
    }

    public void setList(List<V> newList) {
        if(newList == null || newList.isEmpty()) {
            this.list = Lists.newArrayList();
        } else {
            this.list = newList;
        }
    }
}
