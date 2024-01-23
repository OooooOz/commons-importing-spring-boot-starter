package org.commons.importing.autoconfigure;


import com.alibaba.excel.EasyExcelFactory;
import org.commons.importing.Importer;
import org.commons.importing.model.ImportResultVO;

import java.io.InputStream;
import java.util.function.Consumer;

public class DefaultImporter<T> implements Importer<T> {

    private DefaultImporterFactory<T> factory;

    private InputStream file;

    private Consumer dbConsumer;

    private Consumer checkConsumer;

    private ImportResultVO importResultVO;

    DefaultImporter(DefaultImporterFactory factory) {
        this.factory = factory;
    }

    @Override
    public void setFile(InputStream file) {
        this.file = file;
    }

    @Override
    public void setDbConsumer(Consumer dbConsumer) {
        this.dbConsumer = dbConsumer;
    }

    @Override
    public void setCheckConsumer(Consumer checkConsumer) {
        this.checkConsumer = checkConsumer;
    }

    @Override
    public void startImport() {
        CommonDataListener commonDataListener = new CommonDataListener();
        commonDataListener.setConsumer(dbConsumer);
        commonDataListener.setCheckConsumer(checkConsumer);
        EasyExcelFactory.read(file, factory.getEntityClass(), commonDataListener).sheet().doRead();
        importResultVO = commonDataListener.getImportResultVO();
    }

    @Override
    public ImportResultVO getImportResultVO() {
        return importResultVO;
    }
}
