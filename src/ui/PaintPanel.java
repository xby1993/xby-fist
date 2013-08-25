package ui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
public class PaintPanel extends JPanel{




	/**
	 * Žø±³Ÿ°µÄÃæ°å×éŒþ
	 * 
	 * @author ZhongWei Lee
	 */
	
	    
	    /**
	 * 
	 */
	private static final long serialVersionUID = 11111L;
		/**
	     * ±³Ÿ°ÍŒÆ¬
	     */
	    private Image image;
	    
	    /**
	     * ¹¹Ôì·œ·š
	     */
	    public PaintPanel() {
	        super();
	        setOpaque(false);
	        setLayout(null);
	    }
	    
	    /**
	     * ÉèÖÃÍŒÆ¬µÄ·œ·š
	     */
	    public void setImage(Image image) {
	        this.image = image;
	    }
	    
	    @Override
	    protected void paintComponent(Graphics g) {
	        if (image != null) {
	            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	        }
	        super.paintComponent(g);
	    }
	}

