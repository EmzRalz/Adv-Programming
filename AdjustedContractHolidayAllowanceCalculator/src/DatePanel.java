import java.time.DateTimeException;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * As there is more than one opportunity to add a date panel,
 * it made sense to create a class to make the main view
 * class cleaner
 */
public class DatePanel extends JPanel {
	//set the text fields to restrict int length 
	private JTextField year = new JTextField(4);
	private JTextField month = new JTextField(2);
	private JTextField day = new JTextField(2);
	
	public DatePanel() {
		this.setOpaque(isOpaque());
		this.add(day);
		day.setEnabled(true);
		this.add(month);
		month.setEnabled(true);
		this.add(year);
		year.setEnabled(true);
		//has a instructive display border
		this.setBorder(BorderFactory.createTitledBorder("day,  month,  year"));
	}
	
	public LocalDate getDate() {
		//method to gather user input
		int iY = Integer.parseInt(year.getText());
		int iM = Integer.parseInt(month.getText());
		int iD = Integer.parseInt(day.getText());
		LocalDate nDate;
		try {
		nDate = LocalDate.of(iY, iM, iD);
		} catch (DateTimeException e) {
			//the view will initialise a popup to alert the user
			//upon receiving a null value
			return null;
		}
		return nDate;
	}
	
	
}
