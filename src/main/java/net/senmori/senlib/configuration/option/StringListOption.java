package net.senmori.senlib.configuration.option;

import com.google.common.collect.Lists;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.stream.Collectors;

public class StringListOption extends ListOption<String> {
    protected List<String> list = Lists.newArrayList();

    public StringListOption(String key, List<String> defaultValue) {
        super(key, defaultValue);
    }

    public StringListOption(String key, List<String> defaultValue, String description) {
        super(key, defaultValue, description);
    }

    @Override
    public List<String> getValue() {
        return list;
    }

    @Override
    public void setValue(List list) {
        List<String> result = Lists.newArrayList();
        for(Object obj : list) {
            if( !(obj instanceof String) ) continue;
            result.add((String)obj);
        }

        if(!result.isEmpty()) {
            this.list.clear();
            this.list.addAll(result);
            validateListInternal();
        }
    }

    @Override
    public void setList(List<String> value) {
        if(value == null || value.isEmpty()) {
            list = Lists.newArrayList(); // reset array list
            return;
        }
        list.clear();
        list.addAll(value);
        this.list = validateList(list);
    }

    @Override
    public boolean load(FileConfiguration config) {
        if(!config.contains(getPath())) return false;
        if(!(config.get(getPath()) instanceof List)) return false; // it's not a list

        List<String> list = config.getStringList(getPath());
        this.list.addAll(list == null ? Lists.newArrayList() : list);
        validateListInternal();
        return true;
    }

    @Override
    public boolean save(FileConfiguration config) {
        List<String> save = validateList(list);
        config.set(getPath(), save);
        return true;
    }
}
