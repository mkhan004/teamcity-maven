package com.kaplan.Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReportEngine
{
    public static int scriptNumber = 1;
    public static String indexResultFilename;
    public static String failedFilename;
    public static String currentDir;
    public static String currentSuiteName;
    public static boolean newTest = true;
    public static ArrayList<String> description = new ArrayList<String>();

    public static ArrayList<String> actual = new ArrayList<String>();

    public static ArrayList<String> teststatus = new ArrayList<String>();


    public static void startTesting(
            String filename, String testStartTime,
            String env, String rel,String baseURL, Integer port)
    {
        indexResultFilename = filename;
        currentDir = indexResultFilename.substring(0,
                                                   indexResultFilename.lastIndexOf("//"));
        FileWriter fstream = null;
        BufferedWriter out = null;
        try
        {
            // Create file
            fstream = new FileWriter(filename);
            out = new BufferedWriter(fstream);
            String RUN_DATE = TestRunnerUtilities.now("MM/dd/yyyy")
                    .toString();
            String ENVIRONMENT = env;
            String PROJECT = rel;
            out.newLine();
            out.write("<html>\n");
            out.write("<HEAD>\n");
            out.write(" <TITLE>API Test Results</TITLE>\n");
            out.write("</HEAD>\n");
            out.write("<body>\n");
            out.write("<h4 align=center><FONT COLOR=660066 FACE=Times New Roman SIZE=5><b><u> Automation Test Results</u></b></h4>\n");
            out.write("<table  border=1 cellspacing=1 cellpadding=1>\n");
            out.write("<tr>\n");
            out.write("<h4> <FONT COLOR=660000 FACE=Times New Roman SIZE=4> <u>Test Details :</u></h4>\n");
            out.write("<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Times New Roman  SIZE=2.75><b>Project -Environment</b></td>\n");
            out.write("<td width=250 align= left ><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2.75><b>"
                              + PROJECT + " - " + ENVIRONMENT + "</b></td>\n");
            out.write("</tr>\n");
            out.write("<tr>\n");
            out.write("<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Times New Roman  SIZE=2.75><b>Project URL</b></td>\n");
            out.write("<td width=250 align= left ><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2.75><b>"
                              + baseURL + ":" + port + "</b></td>\n");
            out.write("</tr>\n");
            out.write("<tr>\n");
            out.write("<td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Times New Roman SIZE=2.75><b>Run Date</b></td>\n");
            out.write("<td width=250 align=left><FONT COLOR=#153E7E FACE=Times New Roman SIZE=2.75><b>"
                              + RUN_DATE + "</b></td>\n");
            out.write("</tr>\n");
            out.write("<tr>\n");
            out.write("<td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Times New Roman SIZE=2.75><b>Run StartTime</b></td>\n");
            out.write("<td width=250 align=left><FONT COLOR=#153E7E FACE=Times New Roman SIZE=2.75><b>"
                              + testStartTime + "</b></td>\n");
            out.write("</tr>\n");
            out.write("<tr>\n");
            // out.newLine();
            out.write("<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Times New Roman  SIZE=2.75><b>Run EndTime</b></td>\n");
            out.write("<td width=250 align= left ><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2.75><b>END_TIME</b></td>\n");
            out.write("</tr>\n");

            // out.newLine();

            //out.write("<tr>\n");
            //out.write("<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Times New Roman  SIZE=2.75><b>Release</b></td>\n");
            //out.write("<td width=150 align= left ><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2.75><b>"
            //        + RELEASE + "</b></td>\n");
            //out.write("</tr>\n");
            out.write("</table>\n");
            // Close the output stream
            out.close();
        }
        catch (Exception e)
        {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        finally
        {
            fstream = null;
            out = null;
        }
    }

    public static void startFailedTestReport(String filename, String env, String project)
    {
        failedFilename = filename;
        currentDir = failedFilename.substring(0,
                                              failedFilename.lastIndexOf("//"));
        FileWriter fstream = null;
        BufferedWriter out = null;
        String ENVIRONMENT = env;
        String PROJECT = project;
        try
        {
            // Create file
            fstream = new FileWriter(filename);
            out = new BufferedWriter(fstream);
            String RUN_DATE = TestRunnerUtilities.now("MM/dd/yyyy")
                    .toString();
            out.newLine();
            out.write("<html>\n");
            out.write("<HEAD>\n");
            out.write(" <TITLE>API Test Results - FAILED cases </TITLE>\n");
            out.write("</HEAD>\n");
            out.write("<body>\n");
            out.write("<h4 align=center><FONT COLOR=660066 FACE=Times New Roman SIZE=5><b><u> Automation Test Results - FAILED cases</u></b></h4>\n");
            out.write("<table  border=1 cellspacing=1 cellpadding=1 >\n");
            out.write("<tr>\n");
            out.write("<h4> <FONT COLOR=660000 FACE=Times New Roman SIZE=4> <u>Test Details :</u></h4>\n");
            out.write("<td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Times New Roman SIZE=2.75><b>Run Date</b></td>\n");
            out.write("<td width=150 align=left><FONT COLOR=#153E7E FACE=Times New Roman SIZE=2.75><b>"
                              + RUN_DATE + "</b></td>\n");
            out.write("</tr>\n");
            out.write("<tr>\n");
            out.write("<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Times New Roman  SIZE=2.75><b>Environment</b></td>\n");
            out.write("<td width=150 align= left ><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2.75><b>"
                              + PROJECT + " - " + ENVIRONMENT + "</b></td>\n");
            out.write("</tr>\n");
            out.write("<tr>\n");
            out.write("</table>\n");
            // Close the output stream
            out.close();
        }
        catch (Exception e)
        {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        finally
        {
            fstream = null;
            out = null;
        }
    }

    public static void startSuite(String suiteName)
    {
        FileWriter fstream = null;
        BufferedWriter out = null;
        currentSuiteName = suiteName.replaceAll(" ", "_");
        FileWriter fstreamF = null;
        BufferedWriter outF = null;
        try
        {
            fstream = new FileWriter(indexResultFilename, true);
            out = new BufferedWriter(fstream);
            out.write("<h4> <FONT COLOR=660000 FACE= Times New Roman  SIZE=4> <u>"
                              + suiteName + " Report :</u></h4>\n");
            out.write("<table  border=1 cellspacing=1 cellpadding=1 width=100%>\n");
            out.write("<tr>\n");
            out.write("<td width=8%  align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Times New Roman  SIZE=2><b>Test Case#</b></td>\n");
            out.write("<td width=6% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Times New Roman  SIZE=2><b>API Method</b></td>\n");
            out.write("<td width=25% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Times New Roman  SIZE=2><b>Endpoint</b></td>\n");
            out.write("<td width=25% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Times New Roman  SIZE=2><b>Description</b></td>\n");
            out.write("<td width=6% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Times New Roman  SIZE=2><b>Status</b></td>\n");
            out.write("<td width=15% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Times New Roman  SIZE=2><b>Run Start Time</b></td>\n");
            out.write("<td width=15% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Times New Roman  SIZE=2><b>Run End Time</b></td>\n");
            out.write("</tr>\n");
            out.close();
            // For Failed cases Report
            fstreamF = new FileWriter(failedFilename, true);
            outF = new BufferedWriter(fstreamF);
            outF.write("<h4> <FONT COLOR=660000 FACE= Times New Roman  SIZE=4> <u>"
                               + suiteName + " FAILED cases Report :</u></h4>\n");
            outF.write("<table  border=1 cellspacing=1 cellpadding=1 width=100%>\n");
            outF.write("<tr>\n");
            outF.write("<td width=8%  align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Times New Roman  SIZE=2><b>Test Case#</b></td>\n");
            outF.write("<td width=6% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Times New Roman  SIZE=2><b>API Method</b></td>\n");
            outF.write("<td width=25% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Times New Roman  SIZE=2><b>Endpoint</b></td>\n");
            outF.write("<td width=25% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Times New Roman  SIZE=2><b>Description</b></td>\n");
            outF.write("<td width=36% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Times New Roman  SIZE=2><b>Actual</b></td>\n");
            outF.write("</tr>\n");
            outF.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
        finally
        {
            fstream = null;
            out = null;
            fstreamF = null;
            outF = null;
        }
    }

    public static void endSuite()
    {
        FileWriter fstream = null;
        BufferedWriter out = null;
        FileWriter fstreamF = null;
        BufferedWriter outF = null;
        try
        {
            fstream = new FileWriter(indexResultFilename, true);
            out = new BufferedWriter(fstream);
            out.write("</table>\n");
            out.close();
            fstreamF = new FileWriter(failedFilename, true);
            outF = new BufferedWriter(fstreamF);
            outF.write("</table>\n");
            outF.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
        finally
        {
            fstream = null;
            out = null;
            fstreamF = null;
            outF = null;
        }
    }

    public static void addTestCase(
            int TCID, String testMethod,
            String testEndpoint, String testEndPointDesc, String testStartTime,
            String testEndTime, String status)
    {
        newTest = true;
        FileWriter fstream = null;
        BufferedWriter out = null;
        FileWriter fstreamF = null;
        BufferedWriter outF = null;
        try
        {
            newTest = true;
            fstreamF = new FileWriter(failedFilename, true);
            outF = new BufferedWriter(fstreamF);
            if (status.equalsIgnoreCase("Skipped")
                    || status.equalsIgnoreCase("Skip"))
            {
                System.out.println("skipped");
            }
            else
            {
                File f = new File(currentDir + "//" + currentSuiteName + "_"
                                          + TCID + "_" + testMethod.replaceAll(" ", "_")
                                          + ".html");
                f.createNewFile();
                fstream = new FileWriter(currentDir + "//" + currentSuiteName
                                                 + "_" + TCID + "_" + testMethod.replaceAll(" ", "_")
                                                 + ".html");
                out = new BufferedWriter(fstream);
                out.write("<html>");
                out.write("<head>");
                out.write("<title>");
                out.write(testMethod + " Detailed Reports");
                out.write("</title>");
                out.write("</head>");
                out.write("<body>");
                out.write("<h4> <FONT COLOR=660000 FACE=Times New Roman SIZE=4> Detailed Report: " + testMethod + " " + testEndpoint + "</h4>");
                out.write("<table  border=1 cellspacing=1    cellpadding=1 width=100%>");
                out.write("<tr> ");
                out.write("<td align=center width=10%  align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Times New Roman SIZE=2><b>Step/Row#</b></td>");
                out.write("<td align=center width=25% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Times New Roman SIZE=2><b>Description</b></td>");
                out.write("<td align=center width=45% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Times New Roman SIZE=2><b>Actual</b></td>");
                out.write("<td align=center width=10% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Times New Roman SIZE=2><b>Result</b></td>");
                out.write("</tr>");
                if (description != null)
                {
                    for (int i = 0; i < description.size(); i++)
                    {
                        out.write("<tr> ");
                        out.write("<td align=center width=10%><FONT COLOR=#153E7E FACE=Times New Roman SIZE=1><b>.v"
                                          + (i + 1) + "</b></td>");
                        out.write("<td align=left width=25%><FONT COLOR=#153E7E FACE=Times New Roman SIZE=1><b>"
                                          + description.get(i) + "</b></td>");
                        if (description.get(i).contentEquals("Response Output"))
                        {
                            out.write("<td align=left width=45%><FONT COLOR=#153E7E FACE=Times New Roman SIZE=1><b><a href="
                                              + actual.get(i).replaceAll("//", "/") + " target=_blank>Response</a></b></td>\n");

                        }
                        else
                        {
                            out.write("<td align=left width=45%><FONT COLOR=#153E7E FACE=Times New Roman SIZE=1><b>"
                                              + actual.get(i) + "</b></td>");
                        }
                        if (teststatus.get(i).startsWith("Pass"))
                            out.write("<td width=10% align= center  bgcolor=#BCE954><FONT COLOR=#153E7E FACE=Times New Roman SIZE=2><b>"
                                              + teststatus.get(i) + "</b></td>\n");
                        else if (teststatus.get(i).startsWith("Fail"))
                            out.write("<td width=10% align= center  bgcolor=Red><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2><b>"
                                              + teststatus.get(i) + "</b></td>\n");
                        out.write("</tr>");
                        // For failed cases report
                        if (teststatus.get(i).startsWith("Fail"))
                        {
                            outF.write("<td width=8% align= center ><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2><b>"
                                               + TCID + "</b></td>\n");
                            outF.write("<td width=6% align= center ><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2><b>"
                                               + testMethod + "</b></td>\n");
                            outF.write("<td width=25% align= left ><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2><b>"
                                               + testEndpoint + "</b></td>\n");
                            outF.write("<td width=25% align= left ><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2><b>"
                                               + description.get(i) + "</b></td>\n");
                            outF.write("<td width=36% align= left ><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2><b>"
                                               + actual.get(i) + "</b></td>\n");
                            outF.write("</tr>");
                        }
                    }
                }
                out.close();
                outF.close();
            }
            fstream = new FileWriter(indexResultFilename, true);
            out = new BufferedWriter(fstream);
            fstream = new FileWriter(indexResultFilename, true);
            out = new BufferedWriter(fstream);
            out.write("<tr>\n");
            out.write("<td width=8% align= center ><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2><b>"
                              + TCID + "</b></td>\n");
            if (status.equalsIgnoreCase("Skipped")
                    || status.equalsIgnoreCase("Skip"))
                out.write("<td width=6% align= center ><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2><b>"
                                  + testMethod + "</b></td>\n");
            else
                out.write("<td width=6% align= center ><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2><b><a href="
                                  + "./"
                                  + currentSuiteName
                                  + "_"
                                  + TCID
                                  + "_"
                                  + testMethod.replaceAll(" ", "_")
                                  + ".html>"
                                  + testMethod + "</a></b></td>\n");
            out.write("<td width=25% align= left ><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2><b>"
                              + testEndpoint + "</b></td>\n");
            out.write("<td width=25% align= left ><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2><b>"
                              + testEndPointDesc + "</b></td>\n");
            if (status.startsWith("Pass"))
                out.write("<td width=6% align= center  bgcolor=#BCE954><FONT COLOR=#153E7E FACE=Times New Roman SIZE=2><b>"
                                  + status + "</b></td>\n");
            else if (status.startsWith("Fail"))
                out.write("<td width=6% align= center  bgcolor=Red><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2><b>"
                                  + status + "</b></td>\n");
            else if (status.equalsIgnoreCase("Skipped")
                    || status.equalsIgnoreCase("Skip"))
                out.write("<td width=6% align= center  bgcolor=yellow><FONT COLOR=153E7E FACE=Times New Roman SIZE=2><b>"
                                  + status + "</b></td>\n");
            out.write("<td width=15% align= center ><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2><b>"
                              + testStartTime + "</b></td>\n");
            out.write("<td width=15% align= center ><FONT COLOR=#153E7E FACE= Times New Roman  SIZE=2><b>"
                              + testEndTime + "</b></td>\n");
            out.write("</tr>\n");
            scriptNumber++;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        description = new ArrayList<String>();
        actual = new ArrayList<String>();
        teststatus = new ArrayList<String>();
        newTest = false;
    }

    public static void addStep(String desc, String actualResult, String stat)
    {
        description.add(desc);
        actual.add(actualResult);
        teststatus.add(stat);
    }

    public static void updateEndTime(String endTime)
    {
        StringBuffer buf = new StringBuffer();
        try
        {
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream(indexResultFilename);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            // Read File Line By Line
            while ((strLine = br.readLine()) != null)
            {
                if (strLine.indexOf("END_TIME") != -1)
                {
                    strLine = strLine.replace("END_TIME", endTime);
                }
                buf.append(strLine);
            }
            // Close the input stream
            in.close();
            System.out.println(buf);
            FileOutputStream fos = new FileOutputStream(indexResultFilename);
            DataOutputStream output = new DataOutputStream(fos);
            output.writeBytes(buf.toString());
            fos.close();
        }
        catch (Exception e)
        {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
}
