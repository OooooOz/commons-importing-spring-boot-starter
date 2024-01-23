package org.commons.importing.autoconfigure;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;


import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.apache.commons.compress.utils.Lists;
import org.commons.importing.exception.CheckException;
import org.commons.importing.model.ImportResultVO;

import javax.xml.validation.Validator;


public class CommonDataListener extends AnalysisEventListener<Object> {

    private List<Object> list = Lists.newArrayList();

    private Validator validator;

    public ImportResultVO getImportResultVO() {
        return importResultVO;
    }

    private ImportResultVO importResultVO = new ImportResultVO();

    private Consumer dbConsumer;

    private Consumer checkConsumer;

    private String errorFormat = "第%s行数据有误：%s";

    @Override
    public void invoke(Object data, AnalysisContext context) {
//        Set<ConstraintViolation<Object>> violations = validator.validate(data);
//        if (CollectionUtils.isNotEmpty(violations)) {
//            failureCount.incrementAndGet();
//            throw new CheckException(violations.stream().map(v->v.getMessage()).collect(Collectors.toList()).toString());
//        }
        if (checkConsumer != null) {
            checkConsumer.accept(data);
        }
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        dbConsumer.accept(list);
    }

    public void setConsumer(Consumer consumer) {
        this.dbConsumer = consumer;
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        if (exception instanceof CheckException) {
            Integer rowIndex = context.readRowHolder().getRowIndex();
            importResultVO.getFailure().incrementAndGet();
            String format = String.format(errorFormat, ++rowIndex, exception.getMessage());
            importResultVO.getMsg().add(format);
        }
    }

    public void setCheckConsumer(Consumer checkConsumer) {
        this.checkConsumer = checkConsumer;
    }
}
