package partyDuo.com;

import java.security.SecureRandom;

public class RandomAuth {
	private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
	private static final SecureRandom RANDOM = new SecureRandom();
	
	public static String authGenerate(int size) {
		StringBuilder code=new StringBuilder(size);
		for(int i=0; i<size; i++) {
			code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
		}
		return code.toString();
	}
}
