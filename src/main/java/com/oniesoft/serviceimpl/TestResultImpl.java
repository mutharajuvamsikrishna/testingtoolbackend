package com.oniesoft.serviceimpl;

import com.oniesoft.repository.TestResultsRepo;
import com.oniesoft.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestResultImpl implements TestResultService {
    @Autowired
    private TestResultsRepo testResultsRepo;


}
