package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Event implements ActionListener ,ItemListener {
	newfile.addActionListener(this);
	openfile.addActionListener(this);
	savefile.addActionListener(this);
	saveAs.addActionListener(this);
	pageSetup.addActionListener(this);
	pageSetup.addActionListener(this);
	print.addActionListener(this);
	exit.addActionListener(this);
	
	
	cancle.addActionListener(this);
	recover.addActionListener(this);
	cut.addActionListener(this);
	copy.addActionListener(this);
	paste.addActionListener(this);
	delete.addActionListener(this);
	selectAll.addActionListener(this);
	
	autowrap.addActionListener(this);
	font.addActionListener(this);
	
	statusBar.addActionListener(this);
	
	find.addActionListener(this);
	findNext.addActionListener(this);
	replace.addActionListener(this);
	switchTo.addActionListener(this);
	
	helpTopics.addActionListener(this);
	about.addActionListener(this);
	
	pcancle.addActionListener(this);
	precover.addActionListener(this);
	pcut.addActionListener(this);
	pcopy.addActionListener(this);
	ppaste.addActionListener(this);
	pdelete.addActionListener(this);
	pselectall.addActionListener(this);
	
	@Override
	public void actionPerformed(ActionEvent ae) {

	}
	

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}
}

}
