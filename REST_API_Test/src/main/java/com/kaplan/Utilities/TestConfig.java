package com.kaplan.Utilities;

public class TestConfig
{
    private String baseURL;
    private int urlPort;
    private String project;
    private String environment;
    private String testRequestFile;
    private String testValidationFile;
    private String name;
    private String password;
    private String apiKey;
    private String tokenSecret;
    private String sharedSecret;
    private String emailTo;
    private String emailFrom;
    private String emailHost;

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailHost() {
        return emailHost;
    }

    public void setEmailHost(String emailHost) {
        this.emailHost = emailHost;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public String getBaseURL()
    {
        return baseURL;
    }

    public void setBaseURL(String baseURL)
    {
        this.baseURL = baseURL;
    }

    public int getUrlPort()
    {
        return urlPort;
    }

    public void setUrlPort(int urlPort)
    {
        this.urlPort = urlPort;
    }

    public String getProject()
    {
        return project;
    }

    public void setProject(String project)
    {
        this.project = project;
    }

    public String getEnvironment()
    {
        return environment;
    }

    public void setEnvironment(String environment)
    {
        this.environment = environment;
    }

    public String getTestRequestFile()
    {
        return testRequestFile;
    }

    public void setTestRequestFile(String testRequestFile)
    {
        this.testRequestFile = testRequestFile;
    }

    public String getTestValidationFile()
    {
        return testValidationFile;
    }

    public void setTestValidationFile(String testValidationFile)
    {
        this.testValidationFile = testValidationFile;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getApiKey()
    {
        return apiKey;
    }

    public void setApiKey(String apiKey)
    {
        this.apiKey = apiKey;
    }

    public String getSharedSecret()
    {
        return sharedSecret;
    }

    public void setSharedSecret(String sharedSecret)
    {
        this.sharedSecret = sharedSecret;
    }
}
