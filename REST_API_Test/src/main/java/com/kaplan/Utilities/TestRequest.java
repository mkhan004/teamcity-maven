package com.kaplan.Utilities;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.auth0.jwt.internal.com.fasterxml.jackson.core.JsonGenerationException;
import com.auth0.jwt.internal.com.fasterxml.jackson.core.JsonParseException;
import com.auth0.jwt.internal.com.fasterxml.jackson.core.type.TypeReference;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.JsonMappingException;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;
import junit.awtui.TestRunner;

public class TestRequest
{
    private Long id;
    private String method;
    private String endpoint;
    private String headers;
    private String headerInputFile;
    private String reqBody;
    private String input1;
    private String input2;
    private String input3;
    private String input4;
    private String input5;
    private String inputFile;
    private String param1;
    private String param2;
    private String param3;
    private String param4;
    private String param5;
    private String save1;
    private String save2;
    private String save3;
    private String description;
    private String reqContentType;
    private String respContentType;
    private String outPutEndpoint;
    private String hmacSign;
    private String profile;
    private String jwt;

    private static final int DRIFT = 300;
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private static String api_key = TestRunnerUtilities.apiKey;
    private static String share_secret = TestRunnerUtilities.sharedSecret;
    private static String tokenSecret = TestRunnerUtilities.tokenSecret;
    private static String inValid_share_secret = TestRunnerUtilities.invalidSharedSecret;
    private String outputEndpoint;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getEndpoint()
    {
        return endpoint;
    }

    public void setEndpoint(String endpoint)
    {
        this.endpoint = endpoint;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getHeaders()
    {
        return headers;
    }

    public void setHeaders(String headers)
    {
        this.headers = headers;
    }

    public String getReqBody()
    {
        return reqBody;
    }

    public void setReqBody(String reqBody)
    {
        this.reqBody = reqBody;
    }

    public String getInput1()
    {
        return input1;
    }

    public void setInput1(String input1)
    {
        this.input1 = input1;
    }

    public String getInput2()
    {
        return input2;
    }

    public void setInput2(String input2)
    {
        this.input2 = input2;
    }

    public String getInput3()
    {
        return input3;
    }

    public void setInput3(String input3)
    {
        this.input3 = input3;
    }

    public String getInput4()
    {
        return input4;
    }

    public void setInput4(String input4)
    {
        this.input4 = input4;
    }

    public String getInput5()
    {
        return input5;
    }

    public void setInput5(String input5)
    {
        this.input5 = input5;
    }

    public String getInputFile()
    {
        return inputFile;
    }

    public void setInputFile(String inputFile)
    {
        this.inputFile = inputFile;
    }

    public String getHeaderInputFile()
    {
        return headerInputFile;
    }

    public String setHeaderInputFile()
    {
        return this.headerInputFile = headerInputFile;
    }

    public String getParam1()
    {
        System.out.println("Value of param1" + param1);
        return param1;
    }

    public void setParam1(String param1)
    {
        this.param1 = param1;
    }

    public String getParam2()
    {
        return param2;
    }

    public void setParam2(String param2)
    {
        this.param2 = param2;
    }

    public String getParam3()
    {
        return param3;
    }

    public void setParam3(String param3)
    {
        this.param3 = param3;
    }

    public String getParam4()
    {
        return param4;
    }

    public void setParam4(String param4)
    {
        this.param4 = param4;
    }

    public String getParam5()
    {
        return param5;
    }

    public void setParam5(String param5)
    {
        this.param5 = param5;
    }

    public String getSave1()
    {
        return save1;
    }

    public void setSave1(String save1)
    {
        this.save1 = save1;
    }

    public String getSave2()
    {
        return save2;
    }

    public void setSave2(String save2)
    {
        this.save2 = save2;
    }

    public String getSave3()
    {
        return save3;
    }

    public void setSave3(String save3)
    {
        this.save3 = save3;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getReqContentType()
    {
        return reqContentType;
    }

    public void setReqContentType(String reqContentType)
    {
        this.reqContentType = reqContentType;
    }

    public String getRespContentType()
    {
        return respContentType;
    }

    public void setRespContentType(String respContentType)
    {
        this.respContentType = respContentType;
    }

    public String getHMACSign()
    {
        return hmacSign;
    }

    public void setHMACSign(String hmacSign)
    {
        this.hmacSign = hmacSign;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String replaceVariable(
            String stringVariable, String save1AsString,
            String save2AsString, String save3AsString)
    {
        String replacedVarible = "";
        if (stringVariable.equals("{save1}"))
        {
            replacedVarible = save1AsString;
        }
        else if (stringVariable.equals("{save2}"))
        {
            replacedVarible = save2AsString;
        }
        else if (stringVariable.equals("{save3}"))
        {
            replacedVarible = save3AsString;
        }
        return replacedVarible;
    }

    private String replaceVarInString(
            String stringVariable,
            String save1AsString, String save2AsString, String save3AsString)
    {
        if (stringVariable.contains("{save1}"))
        {
            stringVariable = stringVariable.replace("{save1}", save1AsString);
        }
        if (stringVariable.contains("{save2}"))
        {
            stringVariable = stringVariable.replace("{save2}", save2AsString);
        }
        if (stringVariable.contains("{save3}"))
        {
            stringVariable = stringVariable.replace("{save3}", save3AsString);
        }
        if (stringVariable.contains("\""))
        {
            System.out.println("StringVariable before DoubleQuotes" + stringVariable);
            stringVariable = stringVariable.replaceAll("\"", "");
            System.out.println("StringVariable after removing DoubleQuotes:" + stringVariable);
        }
        return stringVariable;
    }

    public String constructQueryString()
    {
        // String URL = null;
        for (int i = 0; i < 5; i++)
        {
            if (endpoint.contains("{") && i == 0)
            {
                String subStr = endpoint.substring(endpoint.indexOf("{"),
                                                   endpoint.indexOf("}") + 1);
                endpoint = endpoint.replace(
                        subStr,
                        replaceFromSaveInput(subStr, input1));
            }
            if (endpoint.contains("{") && i == 1)
            {
                String subStr = endpoint.substring(endpoint.indexOf("{"),
                                                   endpoint.indexOf("}") + 1);
                endpoint = endpoint.replace(
                        subStr,
                        replaceFromSaveInput(subStr, input2));
            }
            if (endpoint.contains("{") && i == 2)
            {
                String subStr = endpoint.substring(endpoint.indexOf("{"),
                                                   endpoint.indexOf("}") + 1);
                endpoint = endpoint.replace(
                        subStr,
                        replaceFromSaveInput(subStr, input3));
            }
            if (endpoint.contains("{") && i == 3)
            {
                String subStr = endpoint.substring(endpoint.indexOf("{"),
                                                   endpoint.indexOf("}") + 1);
                endpoint = endpoint.replace(
                        subStr,
                        replaceFromSaveInput(subStr, input4));
            }
            if (endpoint.contains("{") && i == 4)
            {
                String subStr = endpoint.substring(endpoint.indexOf("{"),
                                                   endpoint.indexOf("}") + 1);
                endpoint = endpoint.replace(
                        subStr,
                        replaceFromSaveInput(subStr, input5));
            }
        }
        /*if (method.equals("GET"))
        {
            if (!param1.contentEquals("null") && !param1.contentEquals(""))
            {

                endpoint = endpoint + replaceParams(param1);
            }
            if (!param2.contentEquals("null") && !param2.contentEquals(""))
            {
                endpoint = endpoint + replaceParams(param2);
            }
            if (!param3.contentEquals("null") && !param3.contentEquals(""))
            {
                endpoint = endpoint + replaceParams(param3);
            }
            if (!param4.contentEquals("null") && !param4.contentEquals(""))
            {
                endpoint = endpoint + replaceParams(param4);
            }
            if (!param5.contentEquals("null") && !param5.contentEquals(""))
            {
                endpoint = endpoint + replaceParams(param5);
            }
        }*/
        return endpoint;
        // return endpoint;
        // {
        // }
        // String mergetOut = "";
        // String mergeParam = "";
        // String[] inputParams = new String[5];
        // // Get Input part and merge with endpoint
        // for (int j = 6; j <= 10; j++)
        // {
        // if (controller.getCellData(sheetname, j, i) != null)
        // {
        // inputParams[j - 6] = controller.getCellData(sheetname, j, i);
        // /*
        // * queryString = queryString + controller.getCellData(sheetname,
        // * j, i) .toString();
        // */
        // }
        // mergetOut = mergeEndpointInput(currentEndpoint, inputParams);
        // }
        // // Get Parametes and merge into Query String
        // for (int j = 12; j <= 16; j++)
        // {
        // if (controller.getCellData(sheetname, j, i) != null)
        // {
        // mergeParam = mergeParam
        // + controller.getCellData(sheetname, j, i);
        // }
        // }
        // return queryString = mergetOut + mergeParam;
    }

    /*public String mergeEndpointInput(String endPoint, String[] inPut)
    {
        String mergedOut = "";
        String[] sEndpoint = endPoint.substring(1).split("\\/");
        int counterInput = 0;
        for (int k = 0; k < sEndpoint.length; k++)
        {
            if (sEndpoint[k].startsWith("{") && !sEndpoint[k].equals("{save1}")
                    && !sEndpoint[k].equals("{save2}")
                    && !sEndpoint[k].equals("{save3}"))
            {
                sEndpoint[k] = inPut[counterInput];
                counterInput++;
            }
            else if (sEndpoint[k].equals("{save1}")
                    && sEndpoint[k].equals("{save2}")
                    && sEndpoint[k].equals("{save3}"))
            {
                sEndpoint[k] = replaceVariable(sEndpoint[k], save1, save2,
                                               save3);
            }
        }
        for (int k = 0; k < sEndpoint.length; k++)
        {
            mergedOut = mergedOut + "/"
                    + replaceVarInString(sEndpoint[k], save1, save2, save3);
        }
        return mergedOut;
    }*/

    public Map<String, String> createHeader(String headerAsFile) throws InterruptedException, NoSuchAlgorithmException, InvalidKeyException, SignatureException
    {
        Date dat= new Date();
        api_key = TestRunnerUtilities.apiKey;
        share_secret = TestRunnerUtilities.sharedSecret;
        tokenSecret = TestRunnerUtilities.tokenSecret;
        inValid_share_secret = TestRunnerUtilities.invalidSharedSecret;

        Map<String, String> parameters = new HashMap<String, String>();
        if(!(headerAsFile == "")&& !(headerAsFile.isEmpty()))
        {
            File headerFile = new File(headerAsFile);

            try
            {
                BufferedReader reader = new BufferedReader(new FileReader(headerAsFile));
                String headerAsString = reader.readLine();
                if (!headerAsString.contentEquals("null")
                        && !headerAsString.contentEquals(""))
                {
                    String[] headerPair = headerAsString.split(";");
                    for (int k = 0; k < headerPair.length; k++)
                    {
                        String[] keyPair = headerPair[k].split(":");
                        parameters.put(keyPair[0], replaceParams(keyPair[1]));
                    }
                }
            }
            catch (IOException fileMissingError)
            {
                System.err.println("Cannot find file " + headerAsFile);
                fileMissingError.printStackTrace();
            }

            if(!getHMACSign().equals(""))
            {
                parameters = signWithHmac(parameters);
            }

            if(!getProfile().equals(""))
            {
                parameters = getJwtToken(parameters);
            }

            return parameters;
        }
        else
        {
            if(!getHMACSign().equals(""))
            {
                parameters = signWithHmac(parameters);
            }
            return parameters;
        }
    }

    private Map<String, String> getJwtToken(Map<String, String> parameters)
    {
        String jsonFilePath = getProfile();
        Map<String, Object> mapObject = ReadProfileIntoMap(jsonFilePath);

        Integer expirySeconds = 2678400; //31 days

        String jwtTokenWithExpiry = GenerateValidJwtToken(mapObject, expirySeconds);

        if(getJwt().equalsIgnoreCase("jwtToken"))
        {
            System.out.println("JWT Token: "+jwtTokenWithExpiry);
            parameters.put("Authorization ", "Bearer " + jwtTokenWithExpiry);
        }
        else if (getJwt().equalsIgnoreCase("jwtTokenMalformed"))
        {
            System.out.println("Malformed JWT: "+jwtTokenWithExpiry+"charactersAreChanged");
            parameters.put("Authorization ", "Bearer " + jwtTokenWithExpiry+"charactersAreChanged");
        }
        else if(getJwt().equalsIgnoreCase("base64profile"))
        {
            String base64Profile = jwtTokenWithExpiry.split(("\\."))[1];
            System.out.println("Profile: "+ jwtTokenWithExpiry.split(("\\."))[1]);
            parameters.put("atom-auth", base64Profile);
        }
        else if(getJwt().equalsIgnoreCase("base64profileMalformed"))
        {
            String base64Profile = jwtTokenWithExpiry.split(("\\."))[1]+"charactersAreChanged";
            System.out.println("Malformed Profile: "+ jwtTokenWithExpiry.split(("\\."))[1]);
            parameters.put("atom-auth", base64Profile);
        }

        return parameters;

    }

    private Map<String, Object> ReadProfileIntoMap(String jsonFilePath)
    {
        Map<String, Object> mapObject = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();

        try
        {
            File jsonFile = new File(jsonFilePath);
            mapObject = mapper.readValue(jsonFile, new TypeReference<Map<String, Object>>() {
            });
        }
        catch (JsonGenerationException e)
        {
            e.printStackTrace();
        }
        catch (JsonMappingException e)
        {
            e.printStackTrace();
        }
        catch (JsonParseException parseException)
        {
            System.out.println("JSON Profile malformed....");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return mapObject;
    }

    private String GenerateValidJwtToken(Map<String, Object> mapObject, Integer expirySeconds)
    {
      //  byte[] secretToBase64 = new Base64(true).decodeBase64(tokenSecret); //Base64 Encoding for Secret. Let this be in here in case we make change again.

        byte[] secretToByte = tokenSecret.getBytes();

        JWTSigner signer = new JWTSigner(secretToByte);

        JWTSigner.Options optionsSeconds = new JWTSigner.Options();
        optionsSeconds.setExpirySeconds(expirySeconds);

        //Create JWT Token
        String jwtToken = signer.sign(mapObject, optionsSeconds);

      //  System.out.println("JWT Token is: "+jwtToken);
        //System.out.println();

        return jwtToken;
    }


    private Map<String, String> signWithHmac(Map<String, String> parameters) throws InterruptedException, NoSuchAlgorithmException, InvalidKeyException, SignatureException
    {
        long epoch;
        String epochString;
        String hmac;
        String httpVerb=getMethod().toUpperCase();
        String pathOfRequest = getOutputEndpoint();
        String requestforSigning = httpVerb + "/" + pathOfRequest;
        System.out.println("requestForSigning is : " + requestforSigning);

        epoch = System.currentTimeMillis() / 1000L;
        System.out.println("Epoch long is: " + epoch);
        epochString = String.valueOf(epoch);
        System.out.println("Epoch is: " + epochString);

        String keyPlusVerbPlusUrlPlusDate = api_key + requestforSigning + epochString;

        System.out.println("Data: "+ keyPlusVerbPlusUrlPlusDate);

        if (getHMACSign().equalsIgnoreCase("sign"))
        {
            hmac = calculateRFC2104HMAC(keyPlusVerbPlusUrlPlusDate,share_secret);
            parameters.put("atom-api-signature", hmac);
            parameters.put("atom-api-date", epochString);
        }
        else if (getHMACSign().equalsIgnoreCase("isign"))
        {
            hmac = calculateRFC2104HMAC( keyPlusVerbPlusUrlPlusDate,inValid_share_secret);
            parameters.put("atom-api-signature", hmac);
            parameters.put("atom-api-date", epochString);
        }
        else if (getHMACSign().equalsIgnoreCase("neglimit")) //Epoch-300
        {
            epoch = epoch - (DRIFT - 10);
            epochString = String.valueOf(epoch);
            System.out.println("epochString: "+epochString);
            String negLimitEpochVerbUrl = api_key + requestforSigning + epochString;

            hmac = calculateRFC2104HMAC( negLimitEpochVerbUrl, share_secret);
            parameters.put("atom-api-signature", hmac);
            parameters.put("atom-api-date", epochString);
        }
        else if (getHMACSign().equalsIgnoreCase("neglimitminusone")) //Epoch-250
        {
            epoch = epoch - (DRIFT - 50);
            epochString = String.valueOf(epoch);
            System.out.println("epochString: "+epochString);
            String negLimitMinusOneEpochVerbUrl = api_key + requestforSigning + epochString;

            hmac = calculateRFC2104HMAC( negLimitMinusOneEpochVerbUrl, share_secret);
            parameters.put("atom-api-signature", hmac);
            parameters.put("atom-api-date", epochString);
        }
        else if (getHMACSign().equalsIgnoreCase("neglimitplusone")) //Epoch-330
        {
            epoch = epoch - (DRIFT + 30);
            epochString = String.valueOf(epoch);
            System.out.println("epochString: "+epochString);
            String negLimitPlusOneEpochVerbUrl = api_key + requestforSigning + epochString;

            hmac = calculateRFC2104HMAC( negLimitPlusOneEpochVerbUrl, share_secret);
            parameters.put("atom-api-signature", hmac);
            parameters.put("atom-api-date", epochString);
        }
        else if (getHMACSign().equalsIgnoreCase("poslimit")) //Epoch+300
        {
            epoch = epoch + (DRIFT - 10);
            epochString = String.valueOf(epoch);
            System.out.println("epochString: "+epochString);
            String posLimitEpochVerbUrl = api_key + requestforSigning + epochString;

            hmac = calculateRFC2104HMAC( posLimitEpochVerbUrl, share_secret);
            parameters.put("atom-api-signature", hmac);
            parameters.put("atom-api-date", epochString);
        }
        else if (getHMACSign().equalsIgnoreCase("poslimitminusone")) //Epoch+300-30
        {
            epoch = epoch + DRIFT - 30;
            epochString = String.valueOf(epoch);
            System.out.println("epochString: "+epochString);
            String posLimitMinusOneEpochVerbUrl = api_key + requestforSigning + epochString;

            hmac = calculateRFC2104HMAC( posLimitMinusOneEpochVerbUrl, share_secret);
            parameters.put("atom-api-signature", hmac);
            parameters.put("atom-api-date", epochString);
        }
        else if (getHMACSign().equalsIgnoreCase("poslimitplusone")) //Epoch+300+1
        {
            epoch = epoch + DRIFT + 20;
            epochString = String.valueOf(epoch);
            System.out.println("epochString: "+epochString);
            String posLimitPlusOneEpochVerbUrl = api_key + requestforSigning + epochString;

            hmac = calculateRFC2104HMAC( posLimitPlusOneEpochVerbUrl, share_secret);
            parameters.put("atom-api-signature", hmac);
            parameters.put("atom-api-date", epochString);
        }
        else if (getHMACSign().equalsIgnoreCase("negValue")) //Negative epoch value
        {
            epoch = -epoch ;
            epochString = String.valueOf(epoch);
            System.out.println("epochString: "+epochString);
            String negValueEpochVerbUrl = api_key + requestforSigning + epochString;

            hmac = calculateRFC2104HMAC( negValueEpochVerbUrl, share_secret);
            parameters.put("atom-api-signature", hmac);
            parameters.put("atom-api-date", epochString);
        }
        else if (getHMACSign().equalsIgnoreCase("fracValue")) //Fractional Epoch value
        {
            double fractionEpoch = (double)epoch*3.1 ;
            epochString = String.valueOf(fractionEpoch);
            System.out.println("epochString: "+epochString);
            String fractionEpochVerbUrl = api_key + requestforSigning + epochString;
            System.out.println("Fractional Epoch Verb URL: "+fractionEpochVerbUrl);

            hmac = calculateRFC2104HMAC( fractionEpochVerbUrl, share_secret);
            parameters.put("atom-api-signature", hmac);
            parameters.put("atom-api-date", epochString);
        }
        else if (getHMACSign().equalsIgnoreCase("malformedepoch")) //Malformed Epoch Value
        {
            String malformedEpochString = String.valueOf(epoch)+ "@b(";
            String malformedEpochVerbUrl = api_key + requestforSigning + malformedEpochString;

            hmac = calculateRFC2104HMAC( malformedEpochVerbUrl, share_secret);
            parameters.put("atom-api-signature", hmac);
            parameters.put("atom-api-date", epochString);
        }
        else if (getHMACSign().equalsIgnoreCase("missingepoch")) //Missing Epoch
        {
            String missingEpochVerbUrl = api_key + requestforSigning;

            hmac = calculateRFC2104HMAC( missingEpochVerbUrl, share_secret);
            parameters.put("atom-api-signature", hmac);
            parameters.put("atom-api-date", epochString);
        }
        else if (getHMACSign().equalsIgnoreCase("missingurlandverb")) //Missing URL and Verb
        {
            epochString = String.valueOf(epoch);
            System.out.println("epochString: "+epochString);
            String missUrlEpoch = api_key + epochString;

            hmac = calculateRFC2104HMAC( missUrlEpoch, share_secret);
            parameters.put("atom-api-signature", hmac);
            parameters.put("atom-api-date", epochString);
        }
        else if (getHMACSign().equalsIgnoreCase("malformedurlandverb")) //Malformed URL and Verb
        {
            epochString = String.valueOf(epoch);
            System.out.println("epochString: "+epochString);
            String malformedUrlEpoch = api_key + requestforSigning + "?()" + epochString;

            hmac = calculateRFC2104HMAC( malformedUrlEpoch, share_secret);
            parameters.put("atom-api-signature", hmac);
            parameters.put("atom-api-date", epochString);
        }
        else if (getHMACSign().equalsIgnoreCase("missingverb")) //Malformed URL and Verb
        {
            epochString = String.valueOf(epoch);
            System.out.println("epochString: "+epochString);
            String missingVerbEpochURL = api_key + "/" + pathOfRequest + epochString;

            hmac = calculateRFC2104HMAC( missingVerbEpochURL, share_secret);
            parameters.put("atom-api-signature", hmac);
            parameters.put("atom-api-date", epochString);
        }
        else if (getHMACSign().equalsIgnoreCase("missingurl")) //Malformed URL and Verb
        {
            epochString = String.valueOf(epoch);
            System.out.println("epochString: "+epochString);
            String missingUrlEpochVerb = api_key + httpVerb + epochString;

            hmac = calculateRFC2104HMAC( missingUrlEpochVerb, share_secret);
            parameters.put("atom-api-signature", hmac);
            parameters.put("atom-api-date", epochString);
        }
        else if (getHMACSign().equalsIgnoreCase("mismatchverb")) //Malformed URL and Verb
        {
            epochString = String.valueOf(epoch);
            System.out.println("epochString: "+epochString);
            String mismatchVerbEpochUrl = api_key + "PATCH" + "/" + pathOfRequest + epochString;

            hmac = calculateRFC2104HMAC( mismatchVerbEpochUrl, share_secret);
            parameters.put("atom-api-signature", hmac);
            parameters.put("atom-api-date", epochString);
        }

        return parameters;
    }

    public Map<String, String> createQueryParameters() throws InterruptedException
    {
        Map<String, String> queryParameters = new HashMap<String, String>();

        if (!getParam1().equals(""))
        {
            String[] keyPair = getParam1().replaceAll("^\"|\"$","").split("=");
            queryParameters.put(
                    keyPair[0],
                    replaceParams(keyPair[1]));
        }

        if (!getParam2().equals(""))
        {
            String[] keyPair = getParam2().replaceAll("^\"|\"$", "").split("=");
            queryParameters.put(
                    keyPair[0],
                    replaceParams(keyPair[1]));
        }
        if (!getParam3().equals(""))
        {
            String[] keyPair = getParam3().replaceAll("^\"|\"$", "").split("=");
            queryParameters.put(
                    keyPair[0],
                    replaceParams(keyPair[1]));
        }
        if (!getParam4().equals(""))
        {
            String[] keyPair = getParam4().replaceAll("^\"|\"$", "").split("=");
            queryParameters.put(
                    keyPair[0],
                    replaceParams(keyPair[1]));
        }
        if (!getParam5().equals(""))
        {
            String[] keyPair = getParam5().replaceAll("^\"|\"$","").split("=");
            queryParameters.put(
                    keyPair[0],
                    replaceParams(keyPair[1]));
        }


        return queryParameters;
    }

    public String getOutputEndpoint()
    {
        String outPutEndpoint = "", params = "";


        if (!param1.contentEquals("null") && !param1.contentEquals(""))
        {

            params = "?" + replaceParams(param1);
        }
        if (!param2.contentEquals("null") && !param2.contentEquals(""))
        {
            params = params + "&" + replaceParams(param2);
        }
        if (!param3.contentEquals("null") && !param3.contentEquals(""))
        {
            params = params + "&" + replaceParams(param3);
        }
        if (!param4.contentEquals("null") && !param4.contentEquals(""))
        {
            params = params + "&" + replaceParams(param4);
        }
        if (!param5.contentEquals("null") && !param5.contentEquals(""))
        {
            params = params + "&" + replaceParams(param5);
        }
        outPutEndpoint = endpoint + params;

        return outPutEndpoint;
    }

    public static String replaceFromSave(String stringVariable, String saveVariable)
    {
        String stringSavedVarialbe = stringVariable;
        String presaveVariable = saveVariable;
        if (saveVariable.startsWith("{"))
        {
            saveVariable = saveVariable.replace("{", "");
            saveVariable = saveVariable.replace("}", "");
            if (saveVariable.contains("."))
            {
                String[] strsplit = saveVariable.split("\\.", 2);
                stringSavedVarialbe = stringSavedVarialbe.replace(presaveVariable, readResponse(Long.valueOf(strsplit[0]), strsplit[1]));
            }
            else
            {
                stringSavedVarialbe = stringSavedVarialbe.replace(stringVariable, stringVariable);
            }
        }
        else
        {

            stringSavedVarialbe = stringSavedVarialbe.replace(stringSavedVarialbe, saveVariable);
        }


        return stringSavedVarialbe;


    }

    public static String replaceFromSaveInput(String stringVariable, String saveVariable)
    {
        String stringSavedVarialbe = stringVariable;
        String presaveVariable = saveVariable;
        if (saveVariable.startsWith("{"))
        {
            saveVariable = saveVariable.replace("{", "");
            saveVariable = saveVariable.replace("}", "");
            if (saveVariable.contains("."))
            {
                String[] strsplit = saveVariable.split("\\.", 2);
                stringSavedVarialbe = stringSavedVarialbe.replace(stringSavedVarialbe, readResponse(Long.valueOf(strsplit[0]), strsplit[1]));
            }
            else
            {
                stringSavedVarialbe = stringSavedVarialbe.replace(stringVariable, stringVariable);
            }
        }
        else
        {

            stringSavedVarialbe = stringSavedVarialbe.replace(stringSavedVarialbe, saveVariable);
        }


        return stringSavedVarialbe;


    }

    public static String readResponse(Long Id, String respElement)
    {
        String retString = "";
        for (int i = 0; i < TestRunnerUtilities.testResponse.size(); i++)
        {
            if (TestRunnerUtilities.testResponse.get(i).getId().equals(Id))
            {
                if (TestRunnerUtilities.testResponse.get(i).getResponse().contentType().equalsIgnoreCase(
                        "application/json; charset=utf-8"))
                {
                    retString = RestAssuredUtil.getJsonPath(TestRunnerUtilities.testResponse.get(i).getResponse(), respElement);

                }
                else if (TestRunnerUtilities.testResponse.get(i).getResponse().contentType().equalsIgnoreCase(
                        "application/xml; charset=utf-8"))
                {
                    retString = RestAssuredUtil.getXmlPath(TestRunnerUtilities.testResponse.get(i).getResponse(), respElement);
                }
            }
        }
        return retString;
    }

    public static String replaceParams(String stringVariable)
    {
        stringVariable = stringVariable.replaceAll("^\"|\"$", "");

        while (stringVariable.contains("{"))
        {
            String subStr = stringVariable.substring(stringVariable.indexOf("{"),
                                                     stringVariable.indexOf("}") + 1);
            stringVariable = replaceFromSave(stringVariable, subStr);


        }
        return stringVariable;
    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
System.out.println("HEX Out :" + formatter.toString());
        return formatter.toString();
    }

    public static String calculateRFC2104HMAC(String data, String key)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
    {
        System.out.println("SharedKEy: " +key);
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);

        return toHexString(mac.doFinal(data.getBytes()));
    }
}
