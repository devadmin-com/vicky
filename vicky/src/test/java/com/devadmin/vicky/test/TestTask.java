package com.devadmin.vicky.test;

import com.devadmin.vicky.Task;


class TestTask implements Task {
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
    public String[] getLabels() {
        return new String[]{"testLabel"};
    }
}
