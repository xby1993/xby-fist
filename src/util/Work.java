package util;

import javax.swing.SwingWorker;

public class Work extends SwingWorker<Boolean,Boolean>{
	private Command com;
	public Work(Command com){
		this.com=com;
	}
	@Override
	protected Boolean doInBackground() throws Exception {
		// TODO Auto-generated method stub
		com.execute();
		return true;
	}
	
}
