package hr.fer.zemris.java.tecaj.hw07.crypto.hextobyte;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw07.crypto.Crypto;

@SuppressWarnings("javadoc")
public class HextobyteTests {

	@Test
	public void testHextobyte1(){
		assertEquals("Marko", new String(Crypto.hextobyte("4d61726b6f")));
		assertEquals("Ovo je tekst koji bi se trebao slagati sa unesenim hex-encoded Stringom.",
				new String(Crypto.hextobyte("4f766f206a652074656b7374206b6f6a692062692073652074726562616f20736c616"
						+ "761746920736120756e6573656e696d206865782d656e636f64656420537472696e676f6d2e")));
	}
	
	@Test 
	public void testHextobyte2(){
		byte[] bytes = new byte[5];
		bytes[0] = 80;
		bytes[1] = 111;
		bytes[2] = 108;
		bytes[3] = 106;
		bytes[4] = 101;
				
		assertEquals(Arrays.toString(bytes), Arrays.toString(Crypto.hextobyte("506f6c6a65")));
	}
	
	@Test 
	public void testHextobyte3(){
		byte[] bytes = "Polje".getBytes();
		
		assertEquals(Arrays.toString(bytes), Arrays.toString(Crypto.hextobyte("506f6c6a65")));
	}
	@Test
	public void testTiny() {
		String hex = "BA";
		byte[] output = Crypto.hextobyte(hex);
		byte[] correct = {(byte) 0xBA};
		assertEquals(true, Arrays.equals(output, correct));
	}
}
