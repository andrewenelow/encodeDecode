import java.util.*;
import tester.Tester;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class PermutationCode {
  // The original list of characters to be encoded
  ArrayList<Character> alphabet = new ArrayList<Character>(
      Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
          'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> code = new ArrayList<Character>(26);

  // A random number generator
  Random rand = new Random();

  // Create a new instance of the encoder/decoder with a new permutation code
  PermutationCode() {
    this.code = this.initEncoder();
  }
  
  /* Template:
   *  Fields:
   *    this.code ... -- ArrayList<Character>
   *  Methods:
   *    this.initEncoder() ... -- ArrayList<Character>
   *    this.coder(String, Boolean) ... -- String
   *    this.encode(String) ... -- String
   *    this.decode(String) ... -- String
   *  Methods of Fields: None
   * 
   */

  // Create a new instance of the encoder/decoder with the given code
  PermutationCode(ArrayList<Character> code) {
    this.code = code;
  }

  // Initialize the encoding permutation of the characters
  ArrayList<Character> initEncoder() {

    ArrayList<Character> randomAlphabet = new ArrayList<Character>(26);
    ArrayList<Character> tempAlphabet = new ArrayList<Character>(26);
    for (int i = 0; i < 26; i++) {
      tempAlphabet.add(this.alphabet.get(i));
    }

    for (int i = 0; i < 26; i++) {
      int r = rand.nextInt(26 - i);
      randomAlphabet.add(tempAlphabet.get(r));
      tempAlphabet.remove(r);
    }
    return randomAlphabet;
  }

  // abstract method that both encodes and decodes messages
  String coder(String s, boolean b) {
    ArrayList<Character> randomAlphabet = this.code;
    ArrayList<Character> normalAlphabet = this.alphabet;
    ArrayList<Character> chars = new ArrayList<Character>(s.length());
    ArrayList<Character> encodedChars = new ArrayList<Character>(s.length());
    StringBuilder builder = new StringBuilder(s.length());

    for (int i = 0; i < s.length(); i++) {
      chars.add(s.charAt(i));
    }
    for (int i = 0; i < s.length(); i++) {
      if (b) {
        encodedChars.add(randomAlphabet.get(normalAlphabet.indexOf(chars.get(i))));
      }
      else {
        encodedChars.add(normalAlphabet.get(randomAlphabet.indexOf(chars.get(i))));
      }
    }
    for (int i = 0; i < s.length(); i++) {
      builder.append(encodedChars.get(i));
    }
    return builder.toString();
  }

  // produce an encoded String from the given String
  String encode(String source) {
    return this.coder(source, true);
  }

  // produce a decoded String from the given String
  String decode(String code) {
    return this.coder(code, false);
  }
}

class ExamplesCode {
  PermutationCode alpha = new PermutationCode(
      new ArrayList<Character>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
          'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')));
  PermutationCode code1 = new PermutationCode(
      new ArrayList<Character>(Arrays.asList('u', 'j', 'd', 'x', 'p', 'l', 'n', 'a', 's', 'y', 'e',
          'k', 'z', 'f', 'i', 't', 'r', 'c', 'm', 'v', 'o', 'w', 'g', 'h', 'q', 'b')));

  boolean testEncode(Tester t) {
    return t.checkExpect(code1.encode("dog"), "xin")
        && t.checkExpect(code1.encode("hello"), "apkki")
        && t.checkExpect(code1.encode("birthday"), "jscvaxuq");
  }

  boolean testDecode(Tester t) {
    return t.checkExpect(code1.decode("xin"), "dog")
        && t.checkExpect(code1.decode("apkki"), "hello")
        && t.checkExpect(code1.decode("jscvaxuq"), "birthday");
  }

  boolean testInitEncoder(Tester t) {
    return t.checkExpect(alpha.initEncoder().size(), 26)
        && t.checkExpect(alpha.initEncoder().containsAll(alpha.code), true);
  }

}
