package org.commons.importing;

import org.commons.importing.model.ImportResultVO;

import java.io.InputStream;
import java.util.function.Consumer;

public interface Importer<T> {

    void setFile(InputStream file);

    void setDbConsumer(Consumer dbConsumer);

    void setCheckConsumer(Consumer checkConsumer);

    void startImport();

    ImportResultVO getImportResultVO();
}
