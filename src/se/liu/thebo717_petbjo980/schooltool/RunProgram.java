package se.liu.thebo717_petbjo980.schooltool;

import se.liu.thebo717_petbjo980.schooltool.viewers.LogInViewer;

/**
 * Creates instance of logInViewer and runs it, which then
 * creates a HomeViewer if the user enters the correct school, username and password
 * Run main to start SchoolTool.
 * This class has no other purposes.
 */
public class RunProgram
{
    public static void main(String[] args) {
	LogInViewer logInViewer = new LogInViewer();
	logInViewer.showLogIn();
    }

}
