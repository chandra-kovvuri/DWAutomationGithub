package Maven_Automation.Maven_Automation;

import Maven_Automation.Maven_Automation.DWworkspace_Creation;
import Maven_Automation.Maven_Automation.Reusable_Action;
import Maven_Automation.Maven_Automation.globalMethods;

public class Reusable_Action {
	public void load_Testcase_Method(globalMethods globalobj,String result_Path,String module_Name,String subModule_Name,String workspaceName)throws Exception{
		//writing switch case statement and loading the corresponding java class 
		//This class is responsible for drive the test cases related to module
		if (module_Name.equalsIgnoreCase("DWHomePage")){
			Reusable_Action.check_Sub_Module_Execution(globalobj,result_Path,module_Name,subModule_Name,workspaceName);
	    }else if(module_Name.equalsIgnoreCase("DWWorkspace")){
	    	Reusable_Action.check_Sub_Module_Execution(globalobj,result_Path,module_Name,subModule_Name,workspaceName);
						
		}
	
	}
	public static void check_Sub_Module_Execution(globalMethods globalobj,String result_Path,String module_Name,String subModule_Name,String workspaceName) throws Exception{
		//in this class checking that whether main module is having sub module or not
		//If main module is having any sub module then creating a class and loading the corresponding method to execute
		//the test cases.
		if (subModule_Name.equalsIgnoreCase("HomePage")){
			System.out.println("executing the submodule test cases."+subModule_Name);
		}else if(subModule_Name.equalsIgnoreCase("WorkspaceCreation")){
			DWworkspace_Creation wc=new DWworkspace_Creation();
			String[] wsType=workspaceName.split(";");
			for (int i=0;i<wsType.length;i++){
				String createWs=wsType[i].toUpperCase();
				switch (createWs){
				case "PROJECT":
					wc.Workspace_Creation_Alltypes(globalobj,result_Path,module_Name,subModule_Name,createWs);
					break;
				case "TEAM":
					wc.Workspace_Creation_Alltypes(globalobj,result_Path,module_Name,subModule_Name,createWs);
					break;
				case "BID":
					System.out.println(createWs);
					break;
				case "COMMUNITY":
					System.out.println(createWs);
					break;
				case "CONTRACT":
					System.out.println(createWs);
					break;
				default:
					System.out.println("Workspace type is not matching. Please give valid value");
					break;
			   }//closing switch case statement
			}//closing inner for loop
		}//closing else if
		else{
			System.out.println("Submodule is not a valid module of"+" "+module_Name+"Open the Driver.xlsx and verify the modules");
		}
	}//closing method
}//closing class
