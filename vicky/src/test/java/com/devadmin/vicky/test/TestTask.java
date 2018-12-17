package com.devadmin.vicky.test;

import com.devadmin.vicky.Task;
import java.util.List;


class TestTask implements Task {

    List<String> labels;

    final String TEST_PROJECT_NAME = "proj";

    @Override
    public String getDescription() {
        return "description";
    }

    @Override
    public String getPriority() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public String getProject() {
        return TEST_PROJECT_NAME;
    }

    @Override
    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
