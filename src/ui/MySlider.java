package ui;

import javax.swing.JSlider;


public class MySlider extends JSlider{
	 private static final long serialVersionUID = 20074L;

	    public MySlider() {
	        super();
	        setDoubleBuffered(true);
	    }

	    public boolean isRequestFocusEnabled() {
	        setValueIsAdjusting(true);
	        repaint();
	        return super.isRequestFocusEnabled();
	    }

	    public void setHideThumb(boolean hide) {
	        ((MySliderUI) getUI()).setHideThumb(hide);
	    }
}
