package zad1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TravelData {
private static File dataDirectory;
	
	public TravelData(File file){
		dataDirectory = file;
	}
	
	public static List<String> getOffersDescriptionsList(String lokalizacja, String formatDaty) {
		List<String> wynik = new ArrayList<String>();
		try {
    	List<String> lista = new ArrayList<String>();
    	
    	List<List<String>> lista2 = new ArrayList<>();
    	lista2 = readfile();
    	
    	int j = lista2.size();
    	
    	String idKraju;
    	String kraj ;
	    String id1;
	    String id2;
	    Locale locale;
	    String idlocale ;
	    Locale jezyk;
	    String cena;
	    Double cenaDouble;
	    NumberFormat nf;
	    //String lokaliz;
	    String nowaCena;
	    Number cenaParse;
	    NumberFormat nf2;
	    
	   // int a , lokal;
	    String cenaZamiej;
	    String waluta;
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	String dataOdString;
    	String dataDoString;
    	String dataOdPoFormacie;
    	String dataDoPoFormacie;
    	String wylot2;
    	String przylot2;
    	Date wylot;
    	Date przylot;
	    SimpleDateFormat sm = new SimpleDateFormat(formatDaty);

    try{
    	for(int i=0; i<j; i++ ){
    		lista = lista2.get(i);
    		idKraju = lista.get(0).substring(0, 2);
    		dataOdString = lista.get(2);
    		dataDoString = lista.get(3);
    		Locale.setDefault(new Locale(idKraju));
    		Locale[] loc1 = Locale.getAvailableLocales();
		    Map<String, Locale> map = new HashMap<String, Locale>();
			for (int ii=0; ii<loc1.length; ii++) {
			        String krajKod = loc1[ii].getCountry();
			        if (krajKod.equals("")) continue;
			        kraj =  loc1[ii].getDisplayCountry();
			        map.put(kraj, loc1[ii]);
			        
			}
		    kraj = lista.get(1).toString();		   
		    locale = (Locale) map.get(kraj);
		    id1 = lokalizacja.substring(0, 2);
		    id2 = lokalizacja.toString().substring(3, 5);
		    jezyk = new Locale(id1,id2);
		    idlocale = locale.getDisplayCountry(jezyk) ;
		    wylot = formatter.parse(dataOdString);
		    przylot = formatter.parse(dataDoString);
		    dataOdPoFormacie= formatter.format(wylot);
		    dataDoPoFormacie = formatter.format(przylot);
		    wylot2 = sm.format(wylot);
		    przylot2 = sm.format(przylot);
		    waluta = lista.get(6);
		    cena = lista.get(5);
		    nf = NumberFormat.getInstance(new Locale(idKraju));
		    cenaParse = nf.parse(cena);
		    cenaDouble = cenaParse.doubleValue();
		    nf2 = NumberFormat.getInstance(jezyk);
		    cenaZamiej = nf2.format(cenaDouble);
		    String miejsceJezioro = "lake";
		    String miejsceMorze = "sea";
		    String miejsceGory = "mountains";
		    String rodzMiejsca = lista.get(4);
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
		    String calosc = idlocale + " " + wylot2 +" "+przylot2+" "+przyroda+" "+cenaZamiej+" "+waluta;
         wynik.add(calosc);
    	}
    	}
    	catch (ParseException e) {
    		System.out.println("błąd parsowania");
    	}
    	catch (Exception exc){
    		System.out.println("błąd!");
    		exc.printStackTrace();
    	}
		
    }
		catch (IOException exc){
			System.out.println("błąd!");
			exc.printStackTrace();
		}
		return wynik;
	}
	
	public static List<List<String>> readfile() throws IOException {
		
		List<List<String>> listy = new ArrayList<>();

		File[] files = dataDirectory.listFiles();
		String wiersz = null;			
		Scanner scan;
		int n = 0;
		for (File file : files) {
			if(file.isFile()){
				BufferedReader reader = new BufferedReader(new FileReader(file));
				while((wiersz = reader.readLine()) !=null) {
				List<String> lista = new ArrayList<String>();
				StringTokenizer st = new StringTokenizer(wiersz, "\t");
				while(st.hasMoreElements()) {
						String element = st.nextElement().toString();
						lista.add(element);
					}
					listy.add(lista);
				}
				reader.close();					
		}
				
	}
		return listy;
	}
	
	
	
}