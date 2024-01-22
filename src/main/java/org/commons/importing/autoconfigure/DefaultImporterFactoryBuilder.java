package org.commons.importing.autoconfigure;

import org.commons.importing.ImporterFactory;
import org.commons.importing.ImporterFactoryBuilder;

class DefaultImporterFactoryBuilder<T> implements ImporterFactoryBuilder<T> {
    private int maxRows;
    private DefaultImporting importing;
    private Class<T> entityClass;

    DefaultImporterFactoryBuilder(DefaultImporting importing) {
        this.importing = importing;
    }

    public ImporterFactoryBuilder<T> maxRows(int paramInt) {
        return null;
    }

    public ImporterFactoryBuilder<T> entityClass(Class<T> paramClass) {
        return null;
    }


    public ImporterFactory<T> build() {
        Object factory = new DefaultImporterFactory();
        ((DefaultImporterFactory) factory).setEntityClass(this.entityClass);
        return (ImporterFactory<T>) factory;
    }
}
