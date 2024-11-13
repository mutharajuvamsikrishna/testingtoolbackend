package com.oniesoft.service;

import com.oniesoft.dto.TestRunRequest;
import com.oniesoft.model.TestRun;

public interface TestRunService {

    TestRun createTestRun(TestRun testRun);

    TestRunRequest addTestRun(TestRunRequest testRunRequest);
}
