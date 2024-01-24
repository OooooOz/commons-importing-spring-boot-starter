package org.commons.importing.autoconfigure;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.commons.importing.exception.CheckException;
import org.commons.importing.model.ImportResultVO;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import cn.hutool.extra.spring.SpringUtil;


public class CommonDataListener extends AnalysisEventListener<Object> {

    private static final String ERROR_FORMAT = "第%s行数据有误：%s";

    private Consumer dbConsumer;

    private Consumer checkConsumer;
    private Integer maxRows;
    private List<Object> list = Lists.newArrayList();
    private Validator validator = SpringUtil.getBean(Validator.class);
    private ImportResultVO importResultVO = new ImportResultVO();

    public ImportResultVO getImportResultVO() {
        return importResultVO;
    }

    @Override
    public void invoke(Object data, AnalysisContext context) {
        Set<ConstraintViolation<Object>> violations = validator.validate(data);
        if (CollectionUtils.isNotEmpty(violations)) {
            throw new CheckException(violations.stream().map(v -> v.getMessage()).collect(Collectors.toList()).toString());
        }
        if (checkConsumer != null) {
            checkConsumer.accept(data);
        }
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (maxRows != null && maxRows > list.size()) {
            importResultVO.addGlobalMsg(String.format("导入数据超过%s条", maxRows));
            return;
        }
        dbConsumer.accept(list);
        importResultVO.getSuccess().addAndGet(list.size());
    }


    @Override
    public void onException(Exception exception, AnalysisContext context) {
        if (exception instanceof CheckException) {
            Integer rowIndex = context.readRowHolder().getRowIndex();
            String format = String.format(ERROR_FORMAT, ++rowIndex, exception.getMessage());
            importResultVO.addFailure(format);
        }
    }

    public void setConsumer(Consumer consumer) {
        this.dbConsumer = consumer;
    }

    public void setCheckConsumer(Consumer checkConsumer) {
        this.checkConsumer = checkConsumer;
    }

    public void setMaxRows(Integer maxRows) {
        this.maxRows = maxRows;
    }
}
