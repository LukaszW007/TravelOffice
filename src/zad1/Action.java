package zad1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

class Action implements ActionListener
{
	public String oferta;
   private String localisation;
   public int row;
   
	public static List<String> lista2 = new ArrayList<>();

   public Action(String c)
   {
      setLocalisation(c);
   }

   public void actionPerformed(ActionEvent event)
   {	
	   for (row = 0; row <Database.rowNo; row++) {
   
 	  Locale.setDefault(new Locale(localisation));
 	  //DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
 	   // NumberFormat nf = NumberFormat.getInstance();
 	   // oferta=df.format(2014-10-06)+" "+nf.format(1234567.1);
 	 
	lista2 = Database.lista3.get(row);
      String lok = lista2.get(0);
      String idKraju = lista2.get(0).substring(0, 2);
      Locale.setDefault(new Locale(idKraju));
      Locale[] dostepneLoc = Locale.getAvailableLocales();
      
      //String[][] map = new array[][];
      
       Map<String, Locale> map = new HashMap<String, Locale>();
       String kraj;
       for (int ii=0; ii<dostepneLoc.length; ii++) {
           String krajKod = dostepneLoc[ii].getCountry();
           if (krajKod.equals("")) continue;
           kraj =  dostepneLoc[ii].getDisplayCountry();
           
          // map.get(kraj,dostepneLoc[ii]);
           map.put(kraj, dostepneLoc[ii]);
           
       }
       kraj = lista2.get(1).toString();
       Locale locale = (Locale) map.get(kraj);
           
       String id1 = localisation;
           
       String id2 = localisation;
           
       
       Locale jezyk = new Locale(id1,id2);
       
       
       String idlocale = locale.getDisplayCountry(jezyk) ;
       String wylot = lista2.get(2);
       String przylot = lista2.get(3);
       
       String miejsceJezioro = "lake";
       String miejsceMorze = "sea";
       String miejsceGory = "mountains";
       String rodzMiejsca = lista2.get(4);
       if(rodzMiejsca.equals(miejsceJezioro)){
       rodzMiejsca = "jezioro";
       }
       else if(rodzMiejsca.equals(miejsceGory)){
       rodzMiejsca = "góry";
       }
       else if(rodzMiejsca.equals(miejsceMorze)){
       rodzMiejsca = "morze";
       }
       ResourceBundle msgs = ResourceBundle.getBundle("zad1.CountryInfo", jezyk);
       String przyroda = msgs.getString(rodzMiejsca);
       String cena = lista2.get(5);
       NumberFormat nf = NumberFormat.getInstance(new Locale(idKraju));
       Number cenaParse = 0;
    try {
    cenaParse = nf.parse(cena);
    } catch (ParseException e1) {
    	System.out.println("błąd !");
    e1.printStackTrace();
    }
    Double cenaDouble = 0.0;
       cenaDouble = cenaParse.doubleValue();
       NumberFormat nf2 = NumberFormat.getInstance(jezyk);
       String cenaZamiej = nf2.format(cenaDouble);
       String waluta = lista2.get(6);
       
       Database.setvalue(idlocale, row, 1);
       Database.setvalue(wylot, row, 2);
       Database.setvalue(przylot, row, 3);
       Database.setvalue(przyroda, row, 4);
       Database.setvalue(cenaZamiej, row, 5);
       Database.setvalue(waluta, row, 6);

       
      
            
	   }};
   

public String getLocalisation() {
	return localisation;
}

public void setLocalisation(String localisation) {
	this.localisation = localisation;
}
}