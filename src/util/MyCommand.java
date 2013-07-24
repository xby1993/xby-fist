package util;

public class MyCommand implements Command{
	private Reciver rec;
	public MyCommand(Reciver rec){
		this.rec=rec;
	}
	public void execute(){
		rec.action();
	}
	
}
