package org.commons.importing.autoconfigure;


import org.commons.importing.Importer;

public class DefaultImporter<T> implements Importer<T> {

    private DefaultImporterFactory<T> factory;

    DefaultImporter(DefaultImporterFactory factory) {
        this.factory = factory;
    }
}
