package com.gbraille.keyboard;

import java.util.Iterator;
import java.util.Map;

public class BrailleCharsUppercaseSlate extends BrailleCharsUppercase {
	public BrailleHashMap map2 = new BrailleHashMap();
	
	public BrailleCharsUppercaseSlate(){
		map2 = (BrailleHashMap) map.clone();
		map.clear();
		reversion();
	}
	
	private void reversion(){
		Iterator it = map2.entrySet().iterator();		
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();
			String brailleCode = pairs.getKey().toString();
			String[] ary = brailleCode.split("");			
			String brailleSlateCode = ary[4] + ary[5] + ary[6] + ary[1] + ary[2] + ary[3];  
			String value = pairs.getValue().toString();
			map.putUniqueKey(brailleSlateCode, value);
		}
	}
}
