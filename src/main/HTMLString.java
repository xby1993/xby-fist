package main;

public class HTMLString {

	/**
	 * @param args
	 */
	private String strs="";
	private String colors="";
	public String getLabelString(String str,String color){
		this.strs=str;
		colors=color;
		return "<html><Strong><font size=4 color="+color+"><Strong><span>" +
						strs
						+"</span></Strong></font><Strong></html>";
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
