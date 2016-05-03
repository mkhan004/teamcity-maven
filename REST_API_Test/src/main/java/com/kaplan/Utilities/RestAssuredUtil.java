package com.kaplan.Utilities;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;


import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.util.AsJson;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.config.EncoderConfig;

import static com.jayway.restassured.RestAssured.*;

public class RestAssuredUtil
{
    private static String name;
    private static String password;
    private static String respContentType;
    private static RequestSpecification reqSpec;
    private static Map<String, String> formParams = new HashMap<String, String>();


    public static void RestAssuredSetup(TestConfig tc, TestRequest tr) throws InterruptedException, NoSuchAlgorithmException, InvalidKeyException, SignatureException
    {
        RestAssured.config = new RestAssuredConfig().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));
        respContentType = tr.getRespContentType();
        name = tc.getName();
        password = tc.getPassword();
        RestAssured.baseURI = tc.getBaseURL();
        RestAssured.port = tc.getUrlPort();
        if (name.length() > 0 && password.length() > 0)
        {
            RestAssured.basic(name, password);
        }
        if (respContentType.equalsIgnoreCase("xml"))
        {

            RestAssured.responseContentType(ContentType.XML);
        }
        else if (respContentType.equalsIgnoreCase("json"))
        {

            RestAssured.responseContentType(ContentType.JSON);
        }
        else if (respContentType.equalsIgnoreCase("html"))
        {

            RestAssured.responseContentType(ContentType.HTML);
        }
        else if (respContentType.equalsIgnoreCase("text"))
        {

            RestAssured.responseContentType(ContentType.TEXT);
        }
        if (!tr.getHeaders().contains("null") && !tr.getHeaders().contains(""))
        {
            RestAssured.requestSpecification.headers(tr.createHeader(tr
                    .getHeaders()));
        }
    }

    public static Response executeTest(TestConfig tc, TestRequest tr) throws IOException, InterruptedException, NoSuchAlgorithmException, InvalidKeyException, SignatureException
    {
        Response response = null;
        RestAssuredSetup(tc, tr);
        if (tr.getMethod().equalsIgnoreCase("Get"))
        {

            response = RestAssured.given().request().headers(tr.createHeader(tr.getHeaders())).queryParams(tr.createQueryParameters()).get(tr.getEndpoint());

        }
        if (tr.getMethod().equalsIgnoreCase("post"))
        {
            String reqContentType = "", bodyText = "";


            if (tr.getReqContentType().equalsIgnoreCase("xml"))
            {
                reqContentType = "application/xml";

            }
            if (tr.getReqContentType().equalsIgnoreCase("json"))
            {
                reqContentType = "application/json";

            }

            if (tr.getReqContentType().equalsIgnoreCase("URLENC"))
            {
                reqContentType = "application/x-www-form-urlencoded";

            }
            if (tr.getInputFile().length() > 2)
            {
                if (tr.getReqContentType().equalsIgnoreCase("URLENC"))
                {
                    generateRequestSpecification(TestRunnerUtilities.readFileAsString(tr.getInputFile()));
                }
                else
                {
                    bodyText = TestRunnerUtilities.readFileAsString(tr.getInputFile());

                }
            }
            if (!tr.getReqBody().isEmpty())
            {
                if (tr.getReqContentType().equalsIgnoreCase("URLENC"))
                {
                    generateRequestSpecification(tr.getReqBody().substring(1, tr.getReqBody().length() - 1));
                }
                else
                {

                    bodyText = tr.getReqBody().substring(1, tr.getReqBody().length() - 1);

                }
            }
            if (tr.getReqContentType().equalsIgnoreCase("URLENC"))
            {

                response = RestAssured.given().request().headers(tr.createHeader(tr.getHeaders())).contentType(reqContentType).spec(reqSpec).basePath(tr.getEndpoint()).post();
            }
            else
            {

                response = RestAssured.given().request().headers(tr.createHeader(tr.getHeaders())).contentType(reqContentType).body(bodyText).basePath(tr.getEndpoint()).queryParams(tr.createQueryParameters()).post();

            }
        }


        if (tr.getMethod().equalsIgnoreCase("put"))
        {
            String reqContentType = "", bodyText = "";

            if (tr.getRespContentType().equalsIgnoreCase("xml"))
            {
                reqContentType = "application/xml";
            }
            if (tr.getRespContentType().equalsIgnoreCase("json"))
            {
                reqContentType = "application/json";

            }
            if (tr.getReqContentType().equalsIgnoreCase("URLENC"))
            {
                reqContentType = "application/x-www-form-urlencoded";

            }
            if (tr.getInputFile().length() > 2)
            {
                if (tr.getReqContentType().equalsIgnoreCase("URLENC"))
                {
                    generateRequestSpecification(TestRunnerUtilities.readFileAsString(tr.getInputFile()));
                }
                else
                {
                    bodyText = TestRunnerUtilities.readFileAsString(tr.getInputFile());
                }
            }
            if (!tr.getReqBody().isEmpty())
            {
                if (tr.getReqContentType().equalsIgnoreCase("URLENC"))
                {
                    generateRequestSpecification(tr.getReqBody().substring(1, tr.getReqBody().length() - 1));
                }
                else
                {

                    bodyText = tr.getReqBody().substring(1, tr.getReqBody().length() - 1);

                }
            }
            if (tr.getReqContentType().equalsIgnoreCase("URLENC"))
            {

                response = RestAssured.given().request().headers(tr.createHeader(tr.getHeaders())).contentType(reqContentType).spec(reqSpec).basePath(tr.getEndpoint()).post();
            }
            else
            {
                response = RestAssured.given().request().headers(tr.createHeader(tr.getHeaders())).contentType(reqContentType).body(bodyText).basePath(tr.getEndpoint()).queryParams(tr.createQueryParameters()).post();

            }

        }
        if (tr.getMethod().equalsIgnoreCase("delete"))
        {
            response = RestAssured.given().request().headers(tr.createHeader(tr.getHeaders())).delete(tr.getEndpoint());
        }
        if (tr.getSave1().contentEquals("yes")
                || tr.getSave1().contentEquals("Yes"))
        {
            TestRunnerUtilities.saveResponse(tr.getId(), response);
        }


        return response;
    }

    private static String saveValue(Response responseAsString, String savefield)
    {
        String respCompare = "";
        try
        {
            // Field verification if response content is JSON
            if (responseAsString.contentType().equalsIgnoreCase(
                    "application/json; charset=utf-8"))
            {
                JsonPath jsonPath = new JsonPath(responseAsString.asString());
                respCompare = jsonPath.getString(savefield);
            }
            // //Field verification
            // if response content is XML
            else if (responseAsString.contentType().equalsIgnoreCase(
                    "application/xml; charset=utf-8"))
            {
                XmlPath xmlPath = new XmlPath(responseAsString.asString());
                respCompare = xmlPath.getString(savefield);
            }
        }
        catch (Exception e)
        {
            TestRunnerUtilities.APPLICATION_LOGS.debug(e.toString());
        }
        return respCompare;
    }

    public static String responseValiadtion(
            Response responseAsString,
            List<TestValidation> tv) throws IOException
    {
        String returnValue = "Pass";
        String savedFilePath = "";
        for (int i = 0; i < tv.size(); i++)
        { // Replace RespText and Respfield with saved values
            tv.get(i).substitutSavedValues();
            //load saved response file for substitution

            if(!responseAsString.asString().isEmpty()) {
                savedFilePath = saveResponseAsFile(
                        TestRunnerUtilities.savedFilePath + "/"
                                + tv.get(i).getDateId(), responseAsString);
            }

            if (!tv.get(i).getRespText().contentEquals("null")
                    && !tv.get(i).getRespText().contentEquals(""))
            {

                // Text Verification

                // TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testStarted name='" + tv.get(i).getRespDescription()+ "']");
                String responseString;
                String responseStringTemp;
                if (tv.get(i).getRespText().startsWith("{"))
                {
                    responseStringTemp = TestRequest.replaceFromSave(tv.get(i).getRespText(), tv.get(i).getRespText());
                    responseString = responseStringTemp;
                }
                else
                    responseStringTemp = tv.get(i).getRespText();

                if(responseStringTemp.contains("\"\""))
                {
                    responseString = responseStringTemp.replace("\"\"", "\"");
                }
                else
                    responseString = responseStringTemp;

               // if (responseAsString.asString()
                 //       .indexOf(responseString) != -1)
                if(responseAsString.asString().contains(responseString))
                {
                    ReportEngine.addStep(tv.get(i).getRespDescription(), tv
                                                 .get(i).getRespText() + " is present in response",
                                         "Pass");
                }
                else
                {
                    if (!tv.get(i).getNegTest().contentEquals("null")
                            && !tv.get(i).getNegTest().contentEquals(""))
                    {
                        if (tv.get(i).getNegTest().equalsIgnoreCase("true"))
                        {
                            ReportEngine.addStep(tv.get(i).getRespDescription(), tv
                                    .get(i).getErrorMessage(), "Pass");

                            returnValue = "Pass";
                        }

                    }
                    else {
                        ReportEngine.addStep(tv.get(i).getRespDescription(), tv
                                .get(i).getErrorMessage(), "Fail");
                        returnValue = "Fail";
                    }
                    //   TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testFailed name='" + tv.get(i).getRespDescription()+"' message='" + tv.get(i).getErrorMessage() + "']");

                }

                //               TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testFinished name='" + tv.get(i).getRespDescription()+ "']");
            }
            if (!tv.get(i).getRespField().contentEquals("null")
                    && !tv.get(i).getRespField().contentEquals(""))
            { // validate Response Field and value
                //             TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testStarted name='" + tv.get(i).getRespDescription()+ "']");
                if (respFieldAndValue(responseAsString, tv.get(i)
                        .getRespField(), tv.get(i).getRespValue()) == "Pass")
                {
                    ReportEngine.addStep(tv.get(i).getRespDescription(), tv
                            .get(i).getRespField()
                            + " matches "
                            + tv.get(i).getRespValue(), "Pass");

                }
                else if (respFieldAndValue(responseAsString, tv.get(i)
                        .getRespField(), tv.get(i).getRespValue()) == "Fail")
                {
                    ReportEngine.addStep(tv.get(i).getRespDescription(), tv
                            .get(i).getErrorMessage(), "Fail");
                    //               TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testFailed name='" + tv.get(i).getRespDescription()+"' message='" + tv.get(i).getErrorMessage() + "']");
                    returnValue = "Fail";
                }
                // validate Response Field
                if (respField(responseAsString, tv.get(i).getRespField(), tv
                        .get(i).getRespValue()) == "Pass")
                {
                    ReportEngine.addStep(tv.get(i).getRespDescription(),
                                         tv.get(i).getRespField()
                                                 + " is present in response ", "Pass");

                }
                else if (respField(responseAsString, tv.get(i).getRespField(),
                                   tv.get(i).getRespValue()) == "Fail")
                {
                    ReportEngine.addStep(tv.get(i).getRespDescription(), tv
                            .get(i).getErrorMessage(), "Fail");
                    //             TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testFailed name='" + tv.get(i).getRespDescription()+"' message='" + tv.get(i).getErrorMessage() + "']");
                    returnValue = "Fail";
                }
                //       TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testFinished name='" + tv.get(i).getRespDescription()+ "']");
            }
            if (!tv.get(i).getRespHeader().contentEquals("null")
                    && !tv.get(i).getRespHeader().contentEquals(""))
            {
                // Compare response header
                //     TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testStarted name='" + tv.get(i).getRespDescription()+ "']");
                String[] headers = tv.get(i).getRespHeader().split(";");
                int mismatch = 0;
                for (int j = 0; i < headers.length; i++)
                {
                    String[] header = headers[j].split(":");
                    if (responseAsString.getHeader(header[0]) != header[1])
                    {
                        mismatch++;
                    }
                }
                if (mismatch == 0)
                {
                    ReportEngine.addStep(tv.get(i).getRespDescription(),
                                         " Response Headers Match", "Pass");

                }
                else
                {
                    ReportEngine.addStep(tv.get(i).getRespDescription(), tv
                            .get(i).getErrorMessage(), "Fail");
                    //       TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testFailed name='" + tv.get(i).getRespDescription()+"' message='" + tv.get(i).getErrorMessage() + "']");
                    returnValue = "Fail";
                }
                // TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testFinished name='" + tv.get(i).getRespDescription()+ "']");

            }
            if (!tv.get(i).getRespHttpCode().contentEquals("null")
                    && !tv.get(i).getRespHttpCode().contentEquals(""))
            {
                // Compare HTTPCODE of response
                // TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testStarted name='" + tv.get(i).getRespDescription()+ "']");
                if ((int) Float.parseFloat(tv.get(i).getRespHttpCode()) == responseAsString
                        .statusCode())
                {
                    ReportEngine
                            .addStep(tv.get(i).getRespDescription(),
                                     " HTTP Code Matches "
                                             + tv.get(i).getRespHttpCode(),
                                     "Pass");

                }
                else
                {
                    ReportEngine.addStep(tv.get(i).getRespDescription(), tv
                            .get(i).getErrorMessage(), "Fail");
                    //   TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testFailed name='" + tv.get(i).getRespDescription()+"' message='" + tv.get(i).getErrorMessage() + "']");
                    returnValue = "Fail";
                }
                //TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testFinished name='" + tv.get(i).getRespDescription()+ "']");
            }
            if (!tv.get(i).getJsonSchema().contentEquals("null")
                    && !tv.get(i).getJsonSchema().contentEquals(""))
            {
                // TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testStarted name='" + tv.get(i).getRespDescription()+ "']");
                // JSON Schema Verification
                JsonValidator VALIDATOR = JsonSchemaFactory.byDefault()
                        .getValidator();
                JsonNode schemaNode = JsonLoader.fromFile(new File(tv.get(i)
                                                                           .getJsonSchema()));
                try{
                    JsonNode data = JsonLoader.fromString(responseAsString
                            .asString());
                    ProcessingReport report = VALIDATOR.validateUnchecked(
                            schemaNode, data);
                    if (report.isSuccess())
                    {
                        ReportEngine.addStep(tv.get(i).getRespDescription(),
                                " Json Schema matches with response"
                                        + tv.get(i).getJsonSchema(), "Pass");

                    }
                    else
                    {
                        ReportEngine.addStep(tv.get(i).getRespDescription(),
                                (String) ((AsJson) report).asJson().toString(),
                                "Fail");
                        //   TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testFailed name='" + tv.get(i).getRespDescription()+"' message='" + tv.get(i).getErrorMessage() + "']");
                        returnValue = "Fail";
                    }
                }catch (Exception e){
                    System.out.println("Json Parse Exception");
                }
                //JsonNode data = JsonLoader.fromString(responseAsString.asString());


                //TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testFinished name='" + tv.get(i).getRespDescription()+ "']");
            }
            if (!tv.get(i).getXmlSchema().contentEquals("null")
                    && !tv.get(i).getXmlSchema().contentEquals(""))
            { // XML Schema Verification
                //TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testStarted name='" + tv.get(i).getRespDescription()+ "']");
                if (validateAgainstXSD((new ByteArrayInputStream(
                                               responseAsString.asByteArray())),
                                       (new ByteArrayInputStream(TestRunnerUtilities
                                                                         .readFileAsString(tv.get(i).getXmlSchema())
                                                                         .getBytes())), tv.get(i).getRespDescription()))
                {
                    ReportEngine.addStep(tv.get(i).getRespDescription(),
                                         " XML Schema matches with response "
                                                 + tv.get(i).getXmlSchema(), "Pass");

                }
                else
                {
                    //  TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testFailed name='" + tv.get(i).getRespDescription()+"' message='" + tv.get(i).getErrorMessage() + "']");
                    returnValue = "Fail";
                }
                //    TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testFinished name='" + tv.get(i).getRespDescription()+ "']");
            }
            if (!tv.get(i).getRespFile().contentEquals("null")
                    && !tv.get(i).getRespFile().contentEquals(""))
            {
                // Compare File with response
                //  TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testStarted name='" + tv.get(i).getRespDescription()+ "']");
                String respFileContent = TestRunnerUtilities
                        .readFileAsString(tv.get(i).getRespFile());
                if (respFileContent.contentEquals(responseAsString.asString()))
                {
                    ReportEngine.addStep(tv.get(i).getRespDescription(),
                                         " Response Matches with file "
                                                 + tv.get(i).getRespHttpCode(), "Pass");

                }
                else
                {
                    ReportEngine.addStep(tv.get(i).getRespDescription(), tv
                            .get(i).getErrorMessage(), "Fail");
                    //    TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testFailed name='" + tv.get(i).getRespDescription()+"' message='" + tv.get(i).getErrorMessage() + "']");
                    returnValue = "Fail";
                }
                //TestRunnerUtilities.APPLICATION_LOGS.debug("##teamcity[testFinished name='" + tv.get(i).getRespDescription()+ "']");
            }
            // Mark Negative tests as "Pass"
            if (!tv.get(i).getNegTest().contentEquals("null")
                    && !tv.get(i).getNegTest().contentEquals(""))
            {
                if (tv.get(i).getNegTest().equalsIgnoreCase("true"))
                {
                    returnValue = "Pass";

                }
            }
            if (i == tv.size() - 1)
            {
                if(!savedFilePath.equals("")) {
                    ReportEngine.addStep(
                            "Response Output",
                            "./"
                                    + savedFilePath.substring(savedFilePath
                                    .lastIndexOf("/") + 1), "Pass");
                }
            }
        }
        return returnValue;
    }

    private static String respFieldAndValue(
            Response responseAsString,
            String respFieldAsAtring, String respValuesAsString)
    {
        String respCompare = "";
        String retString = "";

        if (responseAsString.contentType().equalsIgnoreCase(
                "application/json"))
        {
            JsonPath jsonPath = new JsonPath(responseAsString.asString());
            try{
                respCompare = jsonPath.getString(respFieldAsAtring);
            }catch (Exception e){
                System.out.println("Path is invalid");
            }

        }

        else if (responseAsString.contentType().equalsIgnoreCase(
                "application/json; charset=utf-8"))
        {
            JsonPath jsonPath = new JsonPath(responseAsString.asString());
            respCompare = jsonPath.getString(respFieldAsAtring);
        }
        else if (responseAsString.contentType().equalsIgnoreCase(
                "application/xml; charset=utf-8"))
        {
            XmlPath jsonPath = new XmlPath(responseAsString.asString());
            respCompare = jsonPath.getString(respFieldAsAtring);
        }

        if (respCompare != null && !respCompare.isEmpty())
        {
        if (!respValuesAsString.contentEquals("null")
                && !respValuesAsString.contentEquals(""))
        {
            if (respValuesAsString.contains("{"))
            {
                if (respCompare.equalsIgnoreCase(TestRequest.replaceFromSave(respValuesAsString, respValuesAsString)))
                    retString = "Pass";
                else
                    retString = "Fail";

            }
            else
            {
                if (respCompare.equalsIgnoreCase(respValuesAsString))
                    retString = "Pass";
                else retString = "Fail";
            }

        }
else
            retString = "Fail";
        }
        return retString;
    }

    private static String respField(
            Response responseAsString,
            String respFieldAsAtring, String respValuesAsString)
    {
        Boolean respCompare = null;
        String retString = "";
        if (respValuesAsString.contentEquals("null")
                && respValuesAsString.contentEquals(""))
        {
            if (responseAsString.contentType().equalsIgnoreCase(
                    "application/json"))
            {
                JsonPath jsonPath = new JsonPath(responseAsString.asString());
                respCompare = jsonPath.getBoolean(respFieldAsAtring);
            }

            else if (responseAsString.contentType().equalsIgnoreCase(
                    "application/json; charset=utf-8"))
            {
                JsonPath jsonPath = new JsonPath(responseAsString.asString());
                respCompare = jsonPath.getBoolean(respFieldAsAtring);
            }
            else if (responseAsString.contentType().equalsIgnoreCase(
                    "application/xml; charset=utf-8"))
            {
                XmlPath jsonPath = new XmlPath(responseAsString.asString());
                respCompare = jsonPath.getBoolean(respFieldAsAtring);
            }
            if (respCompare)
            {
                retString = "Pass";
            }
            else
            {
                retString = "Fail";
            }
        }
        return retString;
    }

    private static boolean validateAgainstXSD(
            InputStream xml, InputStream xsd,
            String respDescription)
    {
        try
        {
            SchemaFactory factory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
            return true;
        }
        catch (SAXException e)
        { // TODO Auto-generated catch block
            ReportEngine.addStep(respDescription, e.getStackTrace().toString(),
                                 "Fail");
            return false;
        }
        catch (IOException e)
        { // TODO Auto-generate catch block
            ReportEngine.addStep(respDescription, e.getStackTrace().toString(),
                                 "Fail");
            return false;
        }
    }

    private static String saveResponseAsFile(
            String fileName,
            Response responseAsResponse) throws IOException
    {
        String retVal = "";

        if (responseAsResponse.contentType().equalsIgnoreCase(
                "application/json"))
        {
            retVal = fileName + ".json";
        }

        else if (responseAsResponse.contentType().equalsIgnoreCase(
                "application/json; charset=utf-8"))
        {
            retVal = fileName + ".json";
        }
        else if (responseAsResponse.contentType().equalsIgnoreCase(
                "application/xml; charset=utf-8"))
        {
            retVal = fileName + ".xml";
        }
        else
        {
            retVal = fileName + ".txt";

        }


        FileWriter file = new FileWriter(retVal);
        try
        {
            file.write(responseAsResponse.asString());
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
        return retVal;
    }

    public static String getJsonPath(
            Response responseAsString,
            String pathAsString)
    {
        JsonPath jsonPath = new JsonPath(responseAsString.asString());
        return jsonPath.getString(pathAsString);
    }

    public static String getXmlPath(
            Response responseAsString,
            String pathAsString)
    {
        XmlPath jsonPath = new XmlPath(responseAsString.asString());
        return jsonPath.getString(pathAsString);
    }

    public static void generateRequestSpecification(String formInput)
    {

        String[] params = formInput.split("&");
        for (int i = 0; i < params.length; i++)
        {
            String[] keyVal = params[i].split("=", 2);
            formParams.put(keyVal[0], keyVal[1]);
        }

        reqSpec = new RequestSpecBuilder().addFormParams(formParams).build();

    }
}
