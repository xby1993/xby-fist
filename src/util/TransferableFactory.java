package util;

import java.awt.Image;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class TransferableFactory {
	private Transferable trans;
	private static Object obj;
	private TransferableFactory(){
	}
	public static Transferable getTrans(Object objs){
		obj=objs;
		if(obj instanceof String){
			return new StringSelection((String)obj);
		}else if(obj instanceof Image){
			return new ImageTransform((Image)obj);
		}
		return null;
	}
}
