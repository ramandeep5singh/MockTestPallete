package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import model.ResultBean;

public class EvaluateServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		
		int count = (Integer)session.getAttribute("count");
		String[] physics = new String[count];
		String[] chemistry = new String[count];
		String[] mathematics = new String[count];

		for(int i=0;i<count;i++){
			String p = request.getParameter("p"+(i+1));
			if(p!=null){
				physics[i] = p;
			}
			else{
				physics[i] = "N/A";
			}
		}

		for(int i=0;i<count;i++){
			String c = request.getParameter("c"+(i+1));
			if(c!=null){
				chemistry[i] = c;
			}
			else{
				chemistry[i] = "N/A";
			}
		}

		for(int i=0;i<count;i++){
			String m = request.getParameter("m"+(i+1));
			if(m!=null){
				mathematics[i] = m;
			}
			else{
				mathematics[i] = "N/A";
			}
		}
		
		ServletContext context = getServletContext();
		
		String test = context.getInitParameter("test");

	    String pa = test+"test1/physicsAnswers.txt";
	    String ca = test+"test1/chemistryAnswers.txt";
	    String ma = test+"test1/mathsAnswers.txt";
	    
	    int pScore = 0;
	    int cScore = 0;
	    int mScore = 0;
	    
	    try {
	    	BufferedReader br1 = new BufferedReader(new FileReader(pa));
		    BufferedReader br2 = new BufferedReader(new FileReader(ca));
		    BufferedReader br3 = new BufferedReader(new FileReader(ma));
		    
		    int i=0;
		    String line1;
		    String line2;
		    String line3;
		    
		    while((line1 = br1.readLine())!=null) {
		    	if(line1.equals(physics[i])) {
		    		pScore++;
		    	}
		    	i++;
		    }
		    i=0;
		    
		    while((line2 = br2.readLine())!=null) {
		    	if(line2.equals(chemistry[i])) {
		    		cScore++;
		    	}
		    	i++;
		    }
		    i=0;
		    
		    while((line3 = br3.readLine())!=null) {
		    	if(line3.equals(mathematics[i])) {
		    		mScore++;
		    	}
		    	i++;
		    }
	    }
	    catch(IOException e) {
	    	out.print(e);
	    }

		ResultBean rb = new ResultBean();

		rb.setPhysics(pScore);
		rb.setChemistry(cScore);
		rb.setMaths(mScore);
		
		rb.storeResult();
//
//		int pScore = rb.physicsScore();
//		int cScore = rb.chemistryScore();
//		int mScore = rb.mathematicsScore();
//	
		out.println(pScore);
		out.println(cScore);
		out.println(mScore);

		/*RequestDispatcher rd = request.getRequestDispatcher("result.jsp");
		rd.forward(request,response);*/
	}
}