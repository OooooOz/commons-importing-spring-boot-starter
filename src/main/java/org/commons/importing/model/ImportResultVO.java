package org.commons.importing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;

@Data
public class ImportResultVO {

    private AtomicInteger failure = new AtomicInteger();

    private AtomicInteger success = new AtomicInteger();

    private List<String> msgList = new ArrayList<>();

    public void addGlobalMsg(String msg) {
        msgList = new ArrayList<>();
        msgList.add(msg);
    }

}
