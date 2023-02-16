package com.tutelary.installer.bean;

import java.util.List;
import lombok.Data;

@Data
public class TableIndex {

    private String indexName;

    private boolean unique;

    private String[] columnNames;

}
