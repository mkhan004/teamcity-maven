package com.kaplan.API;

import com.kaplan.Utilities.ReportEngine;
import com.kaplan.Utilities.TestRunnerUtilities;
import org.testng.annotations.*;

import java.io.IOException;

//import Utility.Keywords;
public class TestOrchestrator
{
    @BeforeSuite
    public void initialize() throws IOException
    {
        TestRunnerUtilities.suiteInitialize();
    }

    @BeforeClass
    public static void startTesting()
    {
        TestRunnerUtilities.initiliseTestClass();
    }

    @Test
    //@Parameters({"configpath"})
    public void testApi() throws Exception
    {
        //TestRunnerUtilities.configPath=configpath;
        TestRunnerUtilities.Runtest();
    } // end of class

    @AfterClass
    public static void endScript()
    {
        ReportEngine.updateEndTime(TestRunnerUtilities
                                           .now("dd.MMMMM.yyyy hh.mm.ss aaa"));
    }
}
