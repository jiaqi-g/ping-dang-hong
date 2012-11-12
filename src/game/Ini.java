package game;

import java.io.*;
import org.dtools.ini.*;

public class Ini {
	public static void main( String args[] ) {
		
		
		
		
		  // create an IniFile object
        
        
        
        
        
        
        
        
       // create an IniSection to hold personal data
       IniSection dataSection = ini.addSection( "data" );
       
       // create an IniItem for name
       IniItem nameItem = dataSection.addItem( "name" );
       
       // create an IniItem for age
       IniItem ageItem = dataSection.addItem( "age" );
       
       //--------------------------------------
       
       IniFileWriter writer = new IniFileWriter( ini, file );
       
       try {
           writer.write();
       }
       catch( IOException e ) {
           // exception thrown as an input\output exception occured
           e.printStackTrace();
       }
       
       
       // retrieve a section called "dates"
       IniSection datesSection = ini.getSection( "dates" );
       
      // ... perform some operations of the datesSection
      
      // retrieve a section called "scores"
      IniSection scoresSection = ini.getSection( "scores" );
      
      // ... perform some operations of the scoresSection
      
      // retrieve an item called "start_date"
      IniItem startDateItem = datesSection.getItem( "start_date" );
      
      // ... perform some operations of the startDateItem
      
      // retrieve a section called "scores"
      IniItem endDateItem = datesSection.getItem( "end_date" );
      
      // ... perform some operations of the endDateItem
      
      IniSection personSection = ini.getSection( "person" );
      
      personSection.getItem("name").setValue( "David" );
      personSection.getItem("initial").setValue( 'L' );
      personSection.getItem("age").setValue( 23 );
      
      IniSection javaSection = ini.getSection( "java_experience" );
      
      javaSection.getItem( "years_experience" ).setValue( 4.6 );
      javaSection.getItem( "favourite_class" ).setValue( Math.class );
      
	}
}
