package zad1;

import java.awt.BorderLayout;
import java.io.IOException;
import java.sql.Connection;
import javax.swing.JFrame;
//import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.util.List;
import java.util.Locale;
//import java.util.Map;
import java.util.Vector;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import javax.swing.JButton;

//import javax.swing.JFileChooser;

public class Database {
	public static int rowNo;
	public static String url;
	public static TravelData travelData;
	public static String[][] tabela = new String[10][6];
	private static DefaultTableModel modeltabelli;
	public static List<List<String>> lista3 = new ArrayList<>();
	public static List<String> lista1 = new ArrayList<>();
	public static List<String> lista2 = new ArrayList<>();
	public static int row;

	public Database(String url_bis, TravelData travelData_bis) {
		url = url_bis;
		travelData = travelData_bis;
	}

	public static void create() {
		String nazTabeli = "Podroze";
		Connection connect = null;
		Statement state = null;
		try {
			lista3 = TravelData.readfile();
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
			connect = DriverManager.getConnection(url + ";create=true");

			if (connect != null) {
				DatabaseMetaData dbmd = connect.getMetaData();
				ResultSet rs = dbmd.getTables(null, null,
						nazTabeli.toUpperCase(), null);
				if (rs.next()) {
					System.out.println(rs.getString("TABLE_NAME")
							+ "juz zostala stworzona");
				} else {
					state = connect.createStatement();
					state.executeUpdate("CREATE TABLE "
							+ nazTabeli
							+ "(LOKALIZACJA CHAR(10), COUNTRY CHAR(30), DATA_OD DATE, DATA_DO DATE, TYP_MIEJSCA CHAR(20), CENA DOUBLE, WALUTA CHAR(10) )");
					state.close();
				}
			}
			PreparedStatement statement;
			statement = connect.prepareStatement("INSERT INTO " + nazTabeli
					+ " VALUES(?,?,?,?,?,?,?)");
			List<String> lista1 = new ArrayList<String>();
			int j = lista3.size();
			for (int i = 0; i < j; i++) {
				lista1 = lista3.get(i);
				String a1 = lista1.get(0);
				String a2 = lista1.get(1);
				String a3 = lista1.get(2);
				String a4 = lista1.get(3);
				String a5 = lista1.get(4);
				String a6 = lista1.get(5);
				String miejsce = a1.substring(0, 2);
				NumberFormat format = NumberFormat
						.getNumberInstance(new Locale(miejsce));
				Number a6Parse = format.parse(a6);
				Double a6Double = a6Parse.doubleValue();
				String a7 = lista1.get(6);

				statement.setString(1, a1);
				statement.setString(2, a2);
				statement.setString(3, a3);
				statement.setString(4, a4);
				statement.setString(5, a5);
				statement.setDouble(6, a6Double);
				statement.setString(7, a7);
				statement.executeUpdate();
			}
			statement.close();
		} catch (Exception e) {
			System.out.println("błąd");
			e.printStackTrace();
		}
	}

	public void showGui() {
		Vector rowData = new Vector();
		String[] nazwykolumn = { "lokalizacja", "KRAJ", "Data_od", "Data_do",
				"Kierunek", "Cena", "Waluta" };

		try {

			lista3 = TravelData.readfile();

		} catch (IOException e) {
			System.out.println("błąd");
			e.printStackTrace();
		}

		int j = lista3.size();

		for (int i = 0; i < j; i++) {
			lista1 = lista3.get(i);
			Vector colData = new Vector(lista1);
			rowData.add(colData);

		}
		Vector kolumny = new Vector(Arrays.asList(nazwykolumn));
		JPanel buttonPanel;
		final int DEFAULT_WIDTH = 1000;
		final int DEFAULT_HEIGHT = 1000;
		String kier[] = { "West", "North", "East", "South", "Center" };

		JFrame frame = new JFrame("OFERTY BIURA PODRÓŻY W WIELU JĘZYKACH");
		frame.setLayout(new BorderLayout());
		frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		JButton first = new JButton("Polska");
		JButton second = new JButton("Great Britain");
		JButton third = new JButton("Norway");

		buttonPanel = new JPanel();

		buttonPanel.add(first);
		buttonPanel.add(second);
		buttonPanel.add(third);

		frame.add(buttonPanel, kier[1]);

		Action firstAction = new Action("pl");
		Action secondAction = new Action("en");
		Action thirdAction = new Action("no");

		first.addActionListener(firstAction);
		second.addActionListener(secondAction);
		third.addActionListener(thirdAction);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		setTblModel(new DefaultTableModel(rowData, kolumny));
		JTable table = new JTable(getTblModel());
		rowNo = getTblModel().getRowCount();

		frame.add(new JScrollPane(table));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();

		frame.setVisible(true);
	}

	public static void setvalue(String position, int record, int number) {
		getTblModel().setValueAt(position, record, number);
	}

	public static DefaultTableModel getTblModel() {
		return modeltabelli;
	}

	public void setTblModel(DefaultTableModel modeltabelli) {
		this.modeltabelli = modeltabelli;
	}
}
