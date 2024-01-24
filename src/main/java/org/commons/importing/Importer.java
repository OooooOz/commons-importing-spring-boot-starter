package org.commons.importing;

import java.io.InputStream;
import java.util.function.Consumer;

import org.commons.importing.model.ImportResultVO;

public interface Importer<T> {

    void file(InputStream file);

    void maxRows(Integer maxRows);

    void dbConsumer(Consumer dbConsumer);

    void checkConsumer(Consumer checkConsumer);

    void startImport();

    ImportResultVO getImportResultVO();
}
