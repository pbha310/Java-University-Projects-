import java.awt.*;
import javax.swing.*;

public class SimulationTablePanel extends JPanel{
	public SimulationTablePanel(SimulationTableModel sim) {
		final JTable new_table = new JTable();
		new_table.setModel(sim);
		JScrollPane scroll = new JScrollPane(new_table);
		add(scroll);
	}
};