package util;

public class Invoker {
	private Command com;
	public Invoker(Command com){
		this.com=com;
	}
	public void invoke(){
		com.execute();
	}
}
