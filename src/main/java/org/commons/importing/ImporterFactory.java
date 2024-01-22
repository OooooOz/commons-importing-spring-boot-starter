package org.commons.importing;

public abstract interface ImporterFactory<T> {
    public abstract Importer<T> createImporter();
}
