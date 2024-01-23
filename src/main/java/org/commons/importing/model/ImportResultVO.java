package org.commons.importing.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class ImportResultVO {

    private AtomicInteger failure = new AtomicInteger();

    private AtomicInteger success = new AtomicInteger();

    private List<String> msg = new ArrayList<>();
}
