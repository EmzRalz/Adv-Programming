import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;

/**
 * UserView is the UI, it interacts directly with the HolidayYear class
 * as I feel the whole system and its purpose is too small to warrant a controller
 * 
 * @author Emily
 *
 */
public class UserView extends JFrame implements ActionListener, Observer{
	private JComboBox <Integer> numberOfShifts =  new JComboBox<Integer>();
	private int WIDTH = 450;
	private int HEIGHT = 500;
	private JButton calculate; 
	private JTextField newHours = new JTextField(2);
	private JTextField totalHours = new JTextField(3);;
	private JTextArea totalCalc;
	private JTextArea finalCalc;
	private DatePanel endDate = new DatePanel();
	private HolidayYear hYCalc;
	private JPanel centerSection;
	private Color silver = new Color(211,211,211); //silver
	private Color lightGrey = new Color(245,245,245); //white smoke
	private Color grey = new Color(220,220,220); //gainsboro
	private Popup popup;
	private JButton ok;
	private Popup dateWrong;
	private JButton set;
	private JButton newDate;
	private DatePanel fyDate;
	private JLabel weeks;
	private JFrame jF;
	private JPanel smallPanel;


	public UserView() {
		//set the base layer
		jF = new JFrame();
		jF.setLayout(new BorderLayout());
		jF.setTitle("Holiday Entitlement Calculator");
		jF.setSize(WIDTH, HEIGHT);
		jF.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.calculate = makeButton("Calculate Holiday Accrual");
		calculate.setBackground(lightGrey);

		//setting text areas to calculation results to be displayed, 
		// they are not editable
		finalCalc = new JTextArea("");
		finalCalc.setRows(2);
		totalCalc = new JTextArea("");
		totalCalc.setRows(2);
		totalCalc.setEditable(false);

		//create HolidayYear object
		hYCalc = new HolidayYear();
		//add to notify list if holiday year changes
		hYCalc.addObserver(this);
		//display to user what the current week is
		String display = hYCalc.getCurrentWeekofYear();
		weeks = new JLabel(display);

		JPanel topBorder = new JPanel();
		this.makeCenterPanel();

		//objects to go at the bottom of the frame to allow the financial
		//year date to be amended
		fyDate = new DatePanel();
		newDate = makeButton("Set new holiday year start date");
		//on clicking the newdate button, a datePanel popup will appear
		//and a button to confirm new date
		set = makeButton("Set");
		fyDate.add(set);
		

		//contents for a popup regarding incorrect dates being input
		JLabel warning = new JLabel("The input date is incorrect");
		ok = makeButton("ok");
		smallPanel = new JPanel();
		smallPanel.add(warning);
		smallPanel.add(ok);
		
		
		//add week string to top border, but set to the left
		topBorder.add(weeks, BorderLayout.WEST);
		jF.add(topBorder, BorderLayout.NORTH);
		jF.add(centerSection, BorderLayout.CENTER);
		jF.add(newDate, BorderLayout.SOUTH);


		jF.setVisible(true);

	}

	private void makeCenterPanel() {
		centerSection = new JPanel();
		centerSection.setBackground(lightGrey);

		//gridlayout for the center so everything is equally spaced
		centerSection.setLayout(new GridLayout(4,0));

		//oldDetails is a section for the holidays earned
		JPanel oldDetails = new JPanel();
		oldDetails.setOpaque(isOpaque());
		oldDetails.setBackground(silver);

		//new details is input regarding holidays projected to be earned
		JPanel newDetails = new JPanel();
		newDetails.setOpaque(isOpaque());
		newDetails.setBackground(grey);
		
		JPanel calcDetails = new JPanel();

		//JCombo box to limit the number of shifts input for the calculation 
		numberOfShifts.addItem(1);
		numberOfShifts.addItem(2);
		numberOfShifts.addItem(3);
		numberOfShifts.addItem(4);
		numberOfShifts.addItem(5);

		//labels regarding old hours
		JLabel hoursWorked = new JLabel("How many hours has the employee worked until this date");
		JLabel dateWU = new JLabel("Date employee stopped working old hours");

		//completion of the old hours panel to be added to the grid
		oldDetails.add(dateWU);
		oldDetails.add(endDate);
		oldDetails.add(hoursWorked);
		oldDetails.add(totalHours);

		//instructive labels regarding new hours
		JLabel descriptor = new JLabel("How many shifts will the employee work");
		JLabel newHPW = new JLabel("How many hours per week will they start working");

		newDetails.add(descriptor);
		newDetails.add(numberOfShifts);
		newDetails.add(newHPW);
		newDetails.add(newHours);

		calcDetails.setBackground(lightGrey);
		calcDetails.add(calculate);
		finalCalc.setEditable(false);
		calcDetails.add(finalCalc);
		calcDetails.add(totalCalc);

		JPanel newFinYear = new JPanel();
		newFinYear.add(new JLabel(""));
		newFinYear.add(makeButton("Set new date"));


		centerSection.add(oldDetails);
		centerSection.add(newDetails);
		centerSection.add(calcDetails);

	}
	
	//creates a new button and adds an action listener to it
	private JButton makeButton(String title) {
		
		JButton b = new JButton(title);
		b.setActionCommand(title);
		b.addActionListener(this);
		getContentPane().add(b);
		return b;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == calculate) {
			this.repaint();
			//gets the input data 
			int shifts = (int) numberOfShifts.getSelectedItem();
			String allHours = totalHours.getText();
			String hours = newHours.getText();

			//scanners to converts into integers
			Scanner s = new Scanner(allHours);
			Scanner s2 = new Scanner(hours);
			int tHours = s.nextInt();
			int nHours = s2.nextInt();
			
			//check date is viable, if not end process
			if(checkDate(endDate) == false) {
				return;
			}
			//else pass the data to holiday year for processing
			String y = hYCalc.getWeeksPassed(endDate.getDate(),nHours, tHours, shifts);
			//display the calculated date in the two text boxes
			finalCalc.setText(y);
			totalCalc.setText(hYCalc.yearTotalAccrual());
			//close scanners
			s.close();
			s2.close();

		}
		else if(e.getSource() == newDate) { 
			//initialise pop up to change date
			popup = pf(fyDate);
			popup.show();
		}
		else if(e.getSource() == ok) {
			//closes error message
			dateWrong.hide();
		}
		else {
			//process date provided for new FinancialYear
			if (checkDate(fyDate)== false) {
			//if not a real date end process
				return;
			}
			//else change financial year date
			hYCalc.setFinancialYear(fyDate.getDate());
			popup.hide();
			
		}

	}
	//resets the top border with new weeks passed upon update to financial year
	public void update(String newWeekTotal){
		repaint();
		weeks.setText(newWeekTotal);	

	}
	
	//if the date is incorrect returns false, and shows an error popup
	private boolean checkDate(DatePanel comp) {

		if(comp.getDate() != null) {
			
			return true;

		}
		dateWrong = pf(smallPanel);
		dateWrong.show();
		return false;
	}

	private Popup pf(Component comp) {
		//class to create popups with the contents passed by the caller
		Popup temp = PopupFactory.getSharedInstance().getPopup(jF, comp,100,400);
		return temp;
	}
}
