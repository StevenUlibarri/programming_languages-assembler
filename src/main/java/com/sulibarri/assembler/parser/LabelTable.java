package com.sulibarri.assembler.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by steven on 12/2/15.
 */
public class LabelTable {

    private Map<String, Integer> table;

    public LabelTable() {
        this.table = new HashMap<String, Integer>();
    }

    public void addLabel(String label) {
        table.put(label, 0);
    }

    public void addLabel(String label, int location) {
        table.put(label, location);
    }

    public int getLocationForLabel(String label) {
        return table.get(label);
    }

    public boolean containsLabel(String label) {
        return table.containsKey(label);
    }

}
