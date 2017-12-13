package Import_Export;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Import {

	Calendar cal = Calendar.getInstance();
	private void tableInitialise() throws ParseException{
		cal.setFirstDayOfWeek(Calendar.MONDAY);
	    String start = "25/12/2016";
	    String end = "31/12/2017";
	    SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
	    Calendar scal=Calendar.getInstance();
	    scal.setTime(dateFormat.parse(start));
	    Calendar ecal=Calendar.getInstance();
	    ecal.setTime(dateFormat.parse(end));

	    ArrayList<Date> mondayDates=new ArrayList<>();

	    while(!scal.equals(ecal)){
	        scal.add(Calendar.DATE, 1);
	        if(scal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
	            mondayDates.add(scal.getTime());
	        }
	    }

	    int TOTALROWS = mondayDates.size();
	    for(int i = 0; i < TOTALROWS; i++) {
	   	 allData[i][0] = new SimpleDateFormat("dd/MM/yyyy").format(mondayDates.get(i));
	    }
	}
}
