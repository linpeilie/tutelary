package com.tutelary.installer;

import com.tutelary.installer.constants.DataTypes;
import java.lang.reflect.Field;
import org.springframework.stereotype.Component;

@Component
public class H2TableInstaller extends AbstractTableInstaller {

    @Override
    protected String transType(final Field field) {
        final String dataType = super.transType(field);
        if (DataTypes.JSON.equals(dataType)) {
            return DataTypes.TEXT;
        }
        return dataType;
    }

    @Override
    protected boolean supportTableComment() {
        return false;
    }
}
