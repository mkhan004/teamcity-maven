package com.kaplan.Utilities;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.jayway.restassured.response.Response;

public class TestRunnerUtilities
{
    public static Logger APPLICATION_LOGS = Logger.getLogger("rootLogger");
    public static String environment = null;
    public static String testStatus;
    public static boolean isRun = true;
    public static String save1;
    public static String save2;
    public static String save3;
    public static TestConfig testConf;
    public static String reportPath = "";
    public static String savedFilePath = "";
    public static String configPath = "";
    public static String projectName = "";
    public static ArrayList<TestResponse> testResponse = new ArrayList<TestResponse>();
    public static String apiKey;
    public static String sharedSecret;
    public static String tokenSecret;
    public static String invalidSharedSecret;
    public static String emailTo;
    public static String emailFrom;
    public static String emailHost;
    public static String baseURl;
    public static Integer port;


    public static int totalTc, passTc, failTc;

    // Custom Test Run
    public static boolean customTest = false;
    // Add target Test Case number on this array to run
    // You must add prerequisite Test Case also to get reference values

    public static int[] customTestArray = getCustomTestArray(423, 428);

    public static int[] getCustomTestArray(int start, int stop){
        int[] testArray = new int[stop-start+1];
        int index = 0;
        for(int i=start; i<=stop; i++){
            testArray[index] = i;
            index++;
        }
        return testArray;
    }

    public static void main(String[] args) throws InternalError, IOException, InterruptedException, NoSuchAlgorithmException, InvalidKeyException, SignatureException
    {
        configPath = args[0];
        suiteInitialize();
        initiliseTestClass();
        Runtest();
        ReportEngine.updateEndTime(TestRunnerUtilities
                .now("MM/dd/yyyy hh:mm:ss aaa"));

    }


    public static String now(String dateFormat)
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }

    public static void zip(String filepath)
    {
        try
        {
            File inFolder = new File(filepath);
            File outFolder = new File("Reports.zip");
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    new FileOutputStream(outFolder)));
            BufferedInputStream in = null;
            byte[] data = new byte[1000];
            String files[] = inFolder.list();
            for (int i = 0; i < files.length; i++)
            {
                in = new BufferedInputStream(new FileInputStream(
                        inFolder.getPath() + "/" + files[i]), 1000);
                out.putNextEntry(new ZipEntry(files[i]));
                int count;
                while ((count = in.read(data, 0, 1000)) != -1)
                {
                    out.write(data, 0, count);
                }
                out.closeEntry();
            }
            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public static void suiteInitialize() throws IOException
    {
        BasicConfigurator.configure();
        // loading all the configurations from a property file
        APPLICATION_LOGS.debug("Starting the test suite");
        APPLICATION_LOGS.debug("Loading config files");
        // initialize Input Control sheet


        System.out.println("Working Directory= " + System.getProperty("user.dir"));

        testConf = loadTestConfig(configPath + "/config.csv");
        //testConf = loadTestConfig("./config.csv");
        environment = testConf.getEnvironment();
        projectName = testConf.getProject();
        emailTo = testConf.getEmailTo();
        emailFrom = testConf.getEmailFrom();
        emailHost = testConf.getEmailHost();
        baseURl=testConf.getBaseURL();
        port=testConf.getUrlPort();
    }

    public static void initiliseTestClass()
    {
        String TimeNow = TestRunnerUtilities.now("yyyy-MM-dd_HHmmss");
        reportPath = (System.getProperty("user.dir") + "//CustomOutput//" + TimeNow);
        new File(reportPath).mkdirs();
        savedFilePath = (System.getProperty("user.dir") + "/CustomOutput/" + TimeNow);
        ReportEngine.startTesting(System.getProperty("user.dir")
                        + "/CustomOutput/" + TimeNow + "//index.html",
                TestRunnerUtilities.now("MM/dd/yyyy hh:mm:ss aaa"),
                environment, projectName,baseURl,port);
        ReportEngine.startFailedTestReport(System.getProperty("user.dir")
                + "/CustomOutput/" + TimeNow + "//Failed.html", environment, projectName);
        ReportEngine.startSuite("API Test Suite");
        APPLICATION_LOGS.debug("##teamcity[testSuiteStarted name='Integration Tests']");
        System.out.println("StartTesting");

    }

    public static void Runtest() throws SecurityException,
            IllegalArgumentException, IOException, InterruptedException, NoSuchAlgorithmException, InvalidKeyException, SignatureException
    {
        String startTime = null;
        String returnValue = "";
        String[] emailParameters = {emailTo, emailFrom, emailHost, savedFilePath};
        List<TestRequest> testRequests = loadTestRequests(testConf
                .getTestRequestFile());


        if (customTest){
            totalTc = customTestArray.length;
        }else{
            totalTc = testRequests.size();
        }

        int i = 0;
        for (int t = 0; t < totalTc; t++)
        {

            if (customTest){
                i = customTestArray[t]-1;
            }else{
                i = t;
            }

            // initialize the start time of test
            if (isRun)
            {
                startTime = TestRunnerUtilities
                        .now("MM/dd/yyyy hh:mm:ss aaa");
                //APPLICATION_LOGS.debug("Executing the Method "
                //       + testRequests.get(i).getMethod() + " for endpoint "
                //       + testRequests.get(i).getEndpoint());


                try
                {

                    apiKey = testConf.getApiKey();
                    sharedSecret = testConf.getSharedSecret();
                    tokenSecret = testConf.getTokenSecret();
                    invalidSharedSecret = testConf.getSharedSecret() + "abcdefg";

                    testRequests.get(i).constructQueryString();
                    APPLICATION_LOGS.debug("##teamcity[testStarted name='" + testRequests.get(i).getMethod() + " for endpoint "
                            + testRequests.get(i).getOutputEndpoint() + "']");

                    Response Response = RestAssuredUtil.executeTest(testConf,
                            testRequests.get(i));
                    List<TestValidation> testValidations = loadTestValidations(
                            testConf.getTestValidationFile(),
                            testRequests.get(i).getId());

                    if (Response.asString() != null && !Response.asString().startsWith("[]") || Response.statusCode() == 404 || Response.statusCode() == 400)
                    {
                        returnValue = RestAssuredUtil.responseValiadtion(
                                Response, testValidations);

                    }else if (Response.asString() != null && Response.asString().startsWith("[]") || Response.statusCode() == 404 || Response.statusCode() == 400)
                    {
                        returnValue = RestAssuredUtil.responseValiadtion(
                                Response, testValidations);

                    }
                    else
                    {
                        ReportEngine.addStep("ERROR", "Response is empty ",
                                "Fail");
                        returnValue = "Fail";
                    }
                }
                catch (SecurityException e)
                {
                    e.printStackTrace();
                }
                catch (IllegalArgumentException e)
                {
                    e.printStackTrace();
                } // end of catch
                if (returnValue.startsWith("Fail"))
                {
                    testStatus = "Fail";
                    APPLICATION_LOGS.debug("##teamcity[testFailed name='" + testRequests.get(i).getMethod() + " for endpoint "
                            + testRequests.get(i).getOutputEndpoint() + "']");
                    failTc = failTc + 1;
                }
                else
                    testStatus = returnValue;
                APPLICATION_LOGS.debug("" + testRequests.get(i).getId()
                        + " --- " + testStatus);
                ReportEngine.addTestCase(
                        testRequests.get(i).getId().intValue(), testRequests
                                .get(i).getMethod(), testRequests.get(i)
                                .getOutputEndpoint(), testRequests.get(i)
                                .getDescription(), startTime,
                        TestRunnerUtilities.now("MM/dd/yyyy hh:mm:ss aaa"),
                        testStatus);
                if (returnValue.startsWith("Pass"))
                {
                    passTc = passTc + 1;
                }

            }// end of if
            else
            {
                APPLICATION_LOGS.debug("Skipping the test "
                        + testRequests.get(i).getId());
                testStatus = "Skip";
                // report skipped
                APPLICATION_LOGS.debug("" + testRequests.get(i).getId()
                        + " --- " + testStatus);
                ReportEngine.addTestCase(
                        testRequests.get(i).getId().intValue(), testRequests
                                .get(i).getMethod(), testRequests.get(i)
                                .getOutputEndpoint(), testRequests.get(i)
                                .getDescription(), TestRunnerUtilities
                                .now("MM/dd/yyyy hh:mm:ss aaa"),
                        TestRunnerUtilities.now("MM/dd/yyyy hh:mm:ss aaa"),
                        testStatus);
                APPLICATION_LOGS.debug("##teamcity[testIgnored name='" + testRequests.get(i).getMethod() + " for endpoint "
                        + testRequests.get(i).getOutputEndpoint() + "']");

            } // end of else
            testStatus = null;
            APPLICATION_LOGS.debug("##teamcity[testFinished name='" + testRequests.get(i).getMethod() + " for endpoint "
                    + testRequests.get(i).getOutputEndpoint() + "']");
        } // end of for loop
        ReportEngine.endSuite();
        ReportEngine.updateEndTime(TestRunnerUtilities
                .now("MM/dd/yyyy hh:mm:ss aaa"));
        APPLICATION_LOGS.debug("##teamcity[testSuiteFinished name='Integration Tests']");
        EMailEngine.main(emailParameters);

    }

    public static TestConfig loadTestConfig(String configFile)
            throws IOException
    {
        BufferedReader br = null;
        String line = "";
        TestConfig testConfig = new TestConfig();
        try
        {
            br = new BufferedReader(new FileReader(configFile));
            br.readLine();
            while ((line = br.readLine()) != null)
            {
                // split on comma(',')
                String[] testConfigArray = line.split(",(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))", -1);
                // add values from csv to testConfig object
                testConfig.setBaseURL(testConfigArray[0]);
                testConfig.setUrlPort(Integer.parseInt(testConfigArray[1]));
                testConfig.setProject(testConfigArray[2]);
                testConfig.setEnvironment(testConfigArray[3]);
                testConfig.setTestRequestFile(testConfigArray[4]);
                testConfig.setTestValidationFile(testConfigArray[5]);
                testConfig.setName(testConfigArray[6]);
                testConfig.setPassword(testConfigArray[7]);
                testConfig.setApiKey(testConfigArray[8]);
                testConfig.setSharedSecret(testConfigArray[9]);
                testConfig.setTokenSecret(testConfigArray[10]);//For TokenSecret
                testConfig.setEmailTo(testConfigArray[11]);
                testConfig.setEmailFrom(testConfigArray[12]);
                testConfig.setEmailHost(testConfigArray[13]);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        br.close();
        return testConfig;
    }

    public static List<TestRequest> loadTestRequests(String requestsFile)
            throws IOException
    {
        BufferedReader br = null;
        String line = "";
        List<TestRequest> TestRequests = new ArrayList<TestRequest>();
        try
        {
            br = new BufferedReader(new FileReader(requestsFile));
            br.readLine();
            while ((line = br.readLine()) != null)
            {
                // split on comma(','). Exclude the commas in double quotes.
                String[] testRequestArray = line
                        .split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                TestRequest testRequest = new TestRequest();
                // add values from csv to testRequest object
                testRequest.setId(Long.parseLong(testRequestArray[0]));
                testRequest.setMethod(testRequestArray[1]);
                testRequest.setEndpoint(testRequestArray[2]);
                testRequest.setHeaders(testRequestArray[3]);
                testRequest.setReqBody(testRequestArray[4]);
                testRequest.setInput1(testRequestArray[5]);
                testRequest.setInput2(testRequestArray[6]);
                testRequest.setInput3(testRequestArray[7]);
                testRequest.setInput4(testRequestArray[8]);
                testRequest.setInput5(testRequestArray[9]);
                testRequest.setInputFile(testRequestArray[10]);
                testRequest.setParam1(testRequestArray[11]);
                testRequest.setParam2(testRequestArray[12]);
                testRequest.setParam3(testRequestArray[13]);
                testRequest.setParam4(testRequestArray[14]);
                testRequest.setParam5(testRequestArray[15]);
                testRequest.setSave1(testRequestArray[16]);
                testRequest.setSave2(testRequestArray[17]);
                testRequest.setSave3(testRequestArray[18]);
                testRequest.setDescription(testRequestArray[19]);
                testRequest.setReqContentType(testRequestArray[20]);
                testRequest.setRespContentType(testRequestArray[21]);
                testRequest.setHMACSign(testRequestArray[22]);
                testRequest.setProfile(testRequestArray[23]);
                testRequest.setJwt(testRequestArray[24]);
                TestRequests.add(testRequest);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        br.close();
        return TestRequests;
    }

    public static List<TestValidation> loadTestValidations(
            String ValidationFile, long id) throws IOException
    {
        BufferedReader br = null;
        String line = "";
        List<TestValidation> testValidations = new ArrayList<TestValidation>();
        try
        {
            br = new BufferedReader(new FileReader(ValidationFile));
            br.readLine();
            while ((line = br.readLine()) != null)
            {
                // split on comma(',')
                String[] testValidationArray = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (Long.parseLong(testValidationArray[0]) == id)
                {
                    TestValidation testValidation = new TestValidation();
                    // add values from csv to testValidation object
                    testValidation.setDataId(Long
                            .parseLong(testValidationArray[0]));
                    testValidation.setRespText(testValidationArray[1]);
                    testValidation.setRespHeader(testValidationArray[2]);
                    testValidation.setRespField(testValidationArray[3]);
                    testValidation.setRespValue(testValidationArray[4]);
                    testValidation.setRespFile(testValidationArray[5]);
                    testValidation.setRespHttpCode(testValidationArray[6]);
                    testValidation.setErrorMessage(testValidationArray[7]);
                    testValidation.setJsonSchema(testValidationArray[8]);
                    testValidation.setXmlSchema(testValidationArray[9]);
                    testValidation.setRespDescription(testValidationArray[10]);
                    testValidation.setNegTest(testValidationArray[11]);
                    testValidations.add(testValidation);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        br.close();
        return testValidations;
    }

    public static String readFileAsString(String filePath)
    {
        File file = new File(filePath);
        String content = "";
        try
        {
            content = FileUtils.readFileToString(file);
            content = replaceInFilestring(content, file.getParent());


        }
        catch (IOException ex)
        {
            //  APPLICATION_LOGS.debug(ex);
        }
        return content;
    }

    public static void saveResponse(Long id, Response responseAsResponse)
    {
        TestResponse tr = new TestResponse();
        tr.setId(id);
        tr.setResponse(responseAsResponse);
        testResponse.add(tr);

        System.out.println("totalTc" + totalTc);
        System.out.println("passTc" + passTc);
        System.out.println("failTc" + failTc);

    }

    public static String replaceInFilestring(String content, String filePath) throws IOException
    {
        Date tS = new Date();

        String ReplaceString = "SampleUser" + (tS.getTime() / 1000);

        // StringBuffer buffer = new StringBuffer(content);

        String buffer = content;


        if (content.contains("{username}"))
        {
            buffer = buffer.replace("{username}", ReplaceString);
        }
        if (content.contains("{email}"))
        {
            buffer = buffer.replace("{email}", ReplaceString);
        }

        if (content.contains("{username}") && content.contains("{email}"))


        {
            String retVal = filePath + "/Gen_Account.txt";
            FileWriter file = new FileWriter(retVal);
            try
            {
                file.write(buffer);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                file.flush();
                file.close();
            }
        }

        return buffer;
    }

}

