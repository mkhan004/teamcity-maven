package com.kaplan.Utilities;

public class TestValidation
{
    private Long dataId;
    private String respText;
    private String respHeader;
    private String respField;
    private String respValue;
    private String respFile;
    private String respHttpcode;
    private String errorMessage;
    private String jsonSchema;
    private String xmlSchema;
    private String respDescription;
    private String negTest;

    public Long getDateId()
    {
        return dataId;
    }

    public void setDataId(Long dataId)
    {
        this.dataId = dataId;
    }

    public String getRespText()
    {
        return respText;
    }

    public void setRespText(String respText)
    {
        this.respText = respText;
    }

    public String getRespHeader()
    {
        return respHeader;
    }

    public void setRespHeader(String respHeader)
    {
        this.respHeader = respHeader;
    }

    public String getRespField()
    {
        return respField;
    }

    public void setRespField(String respField)
    {
        this.respField = respField;
    }

    public String getRespValue()
    {
        return respValue;
    }

    public void setRespValue(String respValue)
    {
        this.respValue = respValue;
    }

    public String getRespFile()
    {
        return respFile;
    }

    public void setRespFile(String respFile)
    {
        this.respFile = respFile;
    }

    public String getRespHttpCode()
    {
        return respHttpcode;
    }

    public void setRespHttpCode(String respHttpcode)
    {
        this.respHttpcode = respHttpcode;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public String getJsonSchema()
    {
        return jsonSchema;
    }

    public void setJsonSchema(String jsonSchema)
    {
        this.jsonSchema = jsonSchema;
    }

    public String getXmlSchema()
    {
        return xmlSchema;
    }

    public void setXmlSchema(String xmlSchema)
    {
        this.xmlSchema = xmlSchema;
    }

    public String getRespDescription()
    {
        return respDescription;
    }

    public void setRespDescription(String respDescription)
    {
        this.respDescription = respDescription;
    }

    public String getNegTest()
    {
        return negTest;
    }

    public void setNegTest(String negTest)
    {
        this.negTest = negTest;
    }

    public void substitutSavedValues()
    {
        if (!respText.contentEquals("null"))
        {

            respText = replaceParams(respText);
        }
        if (!respValue.contentEquals("null"))
        {

            //respValue =  TestRequest.replaceParams(respValue);

            respValue = replaceParams(respValue);
        }

        if (!respHeader.contentEquals("null"))
        {
            respHeader = replaceParams(respHeader);
        }
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
        return stringVariable;
    }

    public String replaceFromSave(String stringVariable, String saveVariable)
    {
        String stringSavedVarialbe = stringVariable;
        String presaveVariable = saveVariable;
        if (stringVariable.startsWith("{"))
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
        return stringSavedVarialbe;


    }



	/*public String replaceFromSave(String stringVariable, String saveVariable)
    {
		String preSaveVarialbe = saveVariable;
		stringVariable = stringVariable.replace("{", "");
		stringVariable = stringVariable.replace("}", "");
		if (stringVariable.contains("."))
		{
			String[] strsplit = stringVariable.split("\\.", 2);
			preSaveVarialbe = preSaveVarialbe.replace(preSaveVarialbe,
					readResponse(Long.valueOf(strsplit[0]), strsplit[1]));
		}
		else
		{
			preSaveVarialbe = preSaveVarialbe.replace(preSaveVarialbe,
					stringVariable);
		}
		return preSaveVarialbe;
	}*/

    public String readResponse(Long Id, String respElement)
    {
        String retString = "";
        for (int i = 0; i < TestRunnerUtilities.testResponse.size(); i++)
        {
            if (TestRunnerUtilities.testResponse.get(i).getId() == Id)
            {
                if (TestRunnerUtilities.testResponse.get(i).getResponse()
                        .contentType()
                        .equalsIgnoreCase("application/json; charset=utf-8"))
                {
                    retString = RestAssuredUtil.getJsonPath(
                            TestRunnerUtilities.testResponse.get(i)
                                    .getResponse(), respElement);
                }
                else if (TestRunnerUtilities.testResponse.get(i).getResponse()
                        .contentType()
                        .equalsIgnoreCase("application/xml; charset=utf-8"))
                {
                    retString = RestAssuredUtil.getXmlPath(
                            TestRunnerUtilities.testResponse.get(i)
                                    .getResponse(), respElement);
                }
            }
        }
        return retString;
    }

    public String replaceParams(String stringVariable)
    {
        stringVariable = stringVariable.replaceAll("^\"|\"$", "");
        while (stringVariable.contains("{"))
        {
            String subStr = stringVariable.substring(
                    stringVariable.indexOf("{"),
                    stringVariable.indexOf("}") + 1);
            stringVariable = replaceFromSave(stringVariable, subStr);
        }
        return stringVariable;
    }
}
