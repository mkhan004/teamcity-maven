package com.kaplan.Utilities;

import com.jayway.restassured.response.Response;

/**
 * Created by Harsha on 11/12/14.
 */
public class TestResponse
{

    private Long id;
    private Response response;


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Response getResponse()
    {
        return response;
    }

    public void setResponse(Response response)
    {
        this.response = response;
    }

}
