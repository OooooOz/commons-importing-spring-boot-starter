package org.commons.importing.autoconfigure;


import java.io.InputStream;
import java.util.function.Consumer;

import org.commons.importing.Importer;
import org.commons.importing.model.ImportResultVO;

import com.alibaba.excel.EasyExcelFactory;

public class DefaultImporter<T> implements Importer<T> {

    private DefaultImporterFactory<T> factory;

    private InputStream file;

    private Integer maxRows;

    private Consumer dbConsumer;

    private Consumer checkConsumer;

    private ImportResultVO importResultVO;

    public DefaultImporter(DefaultImporterFactory factory) {
        this.factory = factory;
    }

    @Override
    public void file(InputStream file) {
        this.file = file;
    }

    @Override
    public void maxRows(Integer maxRows) {
        this.maxRows = maxRows;
    }

    @Override
    public void dbConsumer(Consumer dbConsumer) {
        this.dbConsumer = dbConsumer;
    }

    @Override
    public void checkConsumer(Consumer checkConsumer) {
        this.checkConsumer = checkConsumer;
    }

    @Override
    public void startImport() {
        CommonDataListener commonDataListener = new CommonDataListener();
        commonDataListener.setConsumer(dbConsumer);
        commonDataListener.setCheckConsumer(checkConsumer);
        commonDataListener.setMaxRows(maxRows);
        EasyExcelFactory.read(file, factory.getEntityClass(), commonDataListener).sheet().doRead();
        importResultVO = commonDataListener.getImportResultVO();
    }

    @Override
    public ImportResultVO getImportResultVO() {
        return importResultVO;
    }
}
