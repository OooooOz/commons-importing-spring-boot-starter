package org.commons.importing.autoconfigure;


import org.commons.importing.Importer;
import org.commons.importing.ImporterFactory;

public class DefaultImporterFactory<T> implements ImporterFactory<T> {

    private Class<T> entityClass;

    private int maxRows;

    public Importer<T> createImporter() {
        return new DefaultImporter(this);
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }
}
