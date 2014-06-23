package com.sf.tracem.connection;

import java.util.ArrayList;

public class OBJCONFIRMATION {
	
	public OBJCONFIRMATION(){}
	
	ArrayList<IT_CONFIRMATION>  objConfirmation; 
	ArrayList<IT_OPERATIONS> objOperations;
	
	public void setIT_CONFIRMATION(ArrayList<IT_CONFIRMATION> objConfirmation) {
        this.objConfirmation = objConfirmation;
    }
	
	public ArrayList<IT_CONFIRMATION> getIT_CONFIRMATION( ) {
        return objConfirmation;
    }
	
	public void setIT_OPERATIONS(ArrayList<IT_OPERATIONS> objOperations) {
        this.objOperations = objOperations;
    }
	
	public ArrayList<IT_OPERATIONS> getIT_OPERATIONS( ) {
        return objOperations;
    }
}
