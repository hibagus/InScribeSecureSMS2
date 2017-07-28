package digitalquantuminc.inscribesecuresms.UserInterface;

import java.util.HashMap;

/**
 * Created by Bagus Hanindhito on 24/07/2017.
 */

public class GSMEncoderDecoder {

    private static final HashMap<Integer, String> DictionaryEncode;
    private static final HashMap<String, Integer> DictionaryDecode;
    static
    {
        DictionaryEncode = new HashMap<Integer, String>();
        DictionaryEncode.put(0, "@");
        DictionaryEncode.put(1, "£");
        DictionaryEncode.put(2, "$");
        DictionaryEncode.put(3, "¥");
        DictionaryEncode.put(4, "è");
        DictionaryEncode.put(5, "é");
        DictionaryEncode.put(6, "ù");
        DictionaryEncode.put(7, "ì");
        DictionaryEncode.put(8, "ò");
        DictionaryEncode.put(9, "Ç");
        DictionaryEncode.put(10, "Ø");
        DictionaryEncode.put(11, "ø");
        DictionaryEncode.put(12, "Å");
        DictionaryEncode.put(13, "å");
        DictionaryEncode.put(14, "p");
        DictionaryEncode.put(15, "q");
        DictionaryEncode.put(16, "r");
        DictionaryEncode.put(17, "s");
        DictionaryEncode.put(18, "t");
        DictionaryEncode.put(19, "u");
        DictionaryEncode.put(20, "v");
        DictionaryEncode.put(21, "w");
        DictionaryEncode.put(22, "x");
        DictionaryEncode.put(23, "y");
        DictionaryEncode.put(24, "z");
        DictionaryEncode.put(25, "ä");
        DictionaryEncode.put(26, "ö");
        DictionaryEncode.put(27, "ñ");
        DictionaryEncode.put(28, "ü");
        DictionaryEncode.put(29, "à");
        DictionaryEncode.put(30, "¿");
        DictionaryEncode.put(31, "a");
        DictionaryEncode.put(32, "b");
        DictionaryEncode.put(33, "c");
        DictionaryEncode.put(34, "d");
        DictionaryEncode.put(35, "e");
        DictionaryEncode.put(36, "f");
        DictionaryEncode.put(37, "g");
        DictionaryEncode.put(38, "h");
        DictionaryEncode.put(39, "i");
        DictionaryEncode.put(40, "j");
        DictionaryEncode.put(41, "k");
        DictionaryEncode.put(42, "l");
        DictionaryEncode.put(43, "m");
        DictionaryEncode.put(44, "n");
        DictionaryEncode.put(45, "o");
        DictionaryEncode.put(46, "P");
        DictionaryEncode.put(47, "Q");
        DictionaryEncode.put(48, "R");
        DictionaryEncode.put(49, "S");
        DictionaryEncode.put(50, "T");
        DictionaryEncode.put(51, "U");
        DictionaryEncode.put(52, "V");
        DictionaryEncode.put(53, "W");
        DictionaryEncode.put(54, "X");
        DictionaryEncode.put(55, "Y");
        DictionaryEncode.put(56, "Z");
        DictionaryEncode.put(57, "Ä");
        DictionaryEncode.put(58, "Ö");
        DictionaryEncode.put(59, "Ñ");
        DictionaryEncode.put(60, "Ü");
        DictionaryEncode.put(61, "§");
        DictionaryEncode.put(62, "¡");
        DictionaryEncode.put(63, "A");
        DictionaryEncode.put(64, "B");
        DictionaryEncode.put(65, "C");
        DictionaryEncode.put(66, "D");
        DictionaryEncode.put(67, "E");
        DictionaryEncode.put(68, "F");
        DictionaryEncode.put(69, "G");
        DictionaryEncode.put(70, "H");
        DictionaryEncode.put(71, "I");
        DictionaryEncode.put(72, "J");
        DictionaryEncode.put(73, "K");
        DictionaryEncode.put(74, "L");
        DictionaryEncode.put(75, "M");
        DictionaryEncode.put(76, "N");
        DictionaryEncode.put(77, "O");
        DictionaryEncode.put(78, "0");
        DictionaryEncode.put(79, "1");
        DictionaryEncode.put(80, "2");
        DictionaryEncode.put(81, "3");
        DictionaryEncode.put(82, "4");
        DictionaryEncode.put(83, "5");
        DictionaryEncode.put(84, "6");
        DictionaryEncode.put(85, "7");
        DictionaryEncode.put(86, "8");
        DictionaryEncode.put(87, "9");
        DictionaryEncode.put(88, ":");
        DictionaryEncode.put(89, ";");
        DictionaryEncode.put(90, "<");
        DictionaryEncode.put(91, "=");
        DictionaryEncode.put(92, ">");
        DictionaryEncode.put(93, "?");
        DictionaryEncode.put(94, "!");
        DictionaryEncode.put(95, "\"");
        DictionaryEncode.put(96, "#");
        DictionaryEncode.put(97, "¤");
        DictionaryEncode.put(98, "%");
        DictionaryEncode.put(99, "&");
        DictionaryEncode.put(100, "'");
        DictionaryEncode.put(101, "(");
        DictionaryEncode.put(102, ")");
        DictionaryEncode.put(103, "*");
        DictionaryEncode.put(104, "+");
        DictionaryEncode.put(105, ",");
        DictionaryEncode.put(106, "-");
        DictionaryEncode.put(107, ".");
        DictionaryEncode.put(108, "/");
        DictionaryEncode.put(109, "Δ");
        DictionaryEncode.put(110, "_");
        DictionaryEncode.put(111, "Φ");
        DictionaryEncode.put(112, "Γ");
        DictionaryEncode.put(113, "Λ");
        DictionaryEncode.put(114, "Ω");
        DictionaryEncode.put(115, "Π");
        DictionaryEncode.put(116, "Ψ");
        DictionaryEncode.put(117, "Σ");
        DictionaryEncode.put(118, "Θ");
        DictionaryEncode.put(119, "Ξ");
        DictionaryEncode.put(120, "Æ");
        DictionaryEncode.put(121, "æ");
        DictionaryEncode.put(122, "ß");
        DictionaryEncode.put(123, "É");
        DictionaryEncode.put(124, " ");
        DictionaryEncode.put(125, "[");  // \r
        DictionaryEncode.put(126, "]");  // \n
        DictionaryEncode.put(127, "\\"); // consume 2 byte
        //DictionaryEncode.put(127, "\\u001B"); // consume 2 byte

        DictionaryDecode = new HashMap<String, Integer>();
        DictionaryDecode.put("@" ,0  );
        DictionaryDecode.put("£" ,1  );
        DictionaryDecode.put("$" ,2  );
        DictionaryDecode.put("¥" ,3  );
        DictionaryDecode.put("è" ,4  );
        DictionaryDecode.put("é" ,5  );
        DictionaryDecode.put("ù" ,6  );
        DictionaryDecode.put("ì" ,7  );
        DictionaryDecode.put("ò" ,8  );
        DictionaryDecode.put("Ç" ,9  );
        DictionaryDecode.put("Ø" ,10 );
        DictionaryDecode.put("ø" ,11 );
        DictionaryDecode.put("Å" ,12 );
        DictionaryDecode.put("å" ,13 );
        DictionaryDecode.put("p" ,14 );
        DictionaryDecode.put("q" ,15 );
        DictionaryDecode.put("r" ,16 );
        DictionaryDecode.put("s" ,17 );
        DictionaryDecode.put("t" ,18 );
        DictionaryDecode.put("u" ,19 );
        DictionaryDecode.put("v" ,20 );
        DictionaryDecode.put("w" ,21 );
        DictionaryDecode.put("x" ,22 );
        DictionaryDecode.put("y" ,23 );
        DictionaryDecode.put("z" ,24 );
        DictionaryDecode.put("ä" ,25 );
        DictionaryDecode.put("ö" ,26 );
        DictionaryDecode.put("ñ" ,27 );
        DictionaryDecode.put("ü" ,28 );
        DictionaryDecode.put("à" ,29 );
        DictionaryDecode.put("¿" ,30 );
        DictionaryDecode.put("a" ,31 );
        DictionaryDecode.put("b" ,32 );
        DictionaryDecode.put("c" ,33 );
        DictionaryDecode.put("d" ,34 );
        DictionaryDecode.put("e" ,35 );
        DictionaryDecode.put("f" ,36 );
        DictionaryDecode.put("g" ,37 );
        DictionaryDecode.put("h" ,38 );
        DictionaryDecode.put("i" ,39 );
        DictionaryDecode.put("j" ,40 );
        DictionaryDecode.put("k" ,41 );
        DictionaryDecode.put("l" ,42 );
        DictionaryDecode.put("m" ,43 );
        DictionaryDecode.put("n" ,44 );
        DictionaryDecode.put("o" ,45 );
        DictionaryDecode.put("P" ,46 );
        DictionaryDecode.put("Q" ,47 );
        DictionaryDecode.put("R" ,48 );
        DictionaryDecode.put("S" ,49 );
        DictionaryDecode.put("T" ,50 );
        DictionaryDecode.put("U" ,51 );
        DictionaryDecode.put("V" ,52 );
        DictionaryDecode.put("W" ,53 );
        DictionaryDecode.put("X" ,54 );
        DictionaryDecode.put("Y" ,55 );
        DictionaryDecode.put("Z" ,56 );
        DictionaryDecode.put("Ä" ,57 );
        DictionaryDecode.put("Ö" ,58 );
        DictionaryDecode.put("Ñ" ,59 );
        DictionaryDecode.put("Ü" ,60 );
        DictionaryDecode.put("§" ,61 );
        DictionaryDecode.put("¡" ,62 );
        DictionaryDecode.put("A" ,63 );
        DictionaryDecode.put("B" ,64 );
        DictionaryDecode.put("C" ,65 );
        DictionaryDecode.put("D" ,66 );
        DictionaryDecode.put("E" ,67 );
        DictionaryDecode.put("F" ,68 );
        DictionaryDecode.put("G" ,69 );
        DictionaryDecode.put("H" ,70 );
        DictionaryDecode.put("I" ,71 );
        DictionaryDecode.put("J" ,72 );
        DictionaryDecode.put("K" ,73 );
        DictionaryDecode.put("L" ,74 );
        DictionaryDecode.put("M" ,75 );
        DictionaryDecode.put("N" ,76 );
        DictionaryDecode.put("O" ,77 );
        DictionaryDecode.put("0" ,78 );
        DictionaryDecode.put("1" ,79 );
        DictionaryDecode.put("2" ,80 );
        DictionaryDecode.put("3" ,81 );
        DictionaryDecode.put("4" ,82 );
        DictionaryDecode.put("5" ,83 );
        DictionaryDecode.put("6" ,84 );
        DictionaryDecode.put("7" ,85 );
        DictionaryDecode.put("8" ,86 );
        DictionaryDecode.put("9" ,87 );
        DictionaryDecode.put(":" ,88 );
        DictionaryDecode.put(";" ,89 );
        DictionaryDecode.put("<" ,90 );
        DictionaryDecode.put("=" ,91 );
        DictionaryDecode.put(">" ,92 );
        DictionaryDecode.put("?" ,93 );
        DictionaryDecode.put("!" ,94 );
        DictionaryDecode.put("\"",95 );
        DictionaryDecode.put("#" ,96 );
        DictionaryDecode.put("¤" ,97 );
        DictionaryDecode.put("%" ,98 );
        DictionaryDecode.put("&" ,99 );
        DictionaryDecode.put("'" ,100);
        DictionaryDecode.put("(" ,101);
        DictionaryDecode.put(")" ,102);
        DictionaryDecode.put("*" ,103);
        DictionaryDecode.put("+" ,104);
        DictionaryDecode.put("," ,105);
        DictionaryDecode.put("-" ,106);
        DictionaryDecode.put("." ,107);
        DictionaryDecode.put("/" ,108);
        DictionaryDecode.put("Δ" ,109);
        DictionaryDecode.put("_" ,110);
        DictionaryDecode.put("Φ" ,111);
        DictionaryDecode.put("Γ" ,112);
        DictionaryDecode.put("Λ" ,113);
        DictionaryDecode.put("Ω" ,114);
        DictionaryDecode.put("Π" ,115);
        DictionaryDecode.put("Ψ" ,116);
        DictionaryDecode.put("Σ" ,117);
        DictionaryDecode.put("Θ" ,118);
        DictionaryDecode.put("Ξ" ,119);
        DictionaryDecode.put("Æ" ,120);
        DictionaryDecode.put("æ" ,121);
        DictionaryDecode.put("ß" ,122);
        DictionaryDecode.put("É" ,123);
        DictionaryDecode.put(" " ,124);
        DictionaryDecode.put("[" ,125); // consume 2 byte
        DictionaryDecode.put("]" ,126); // consume 2 byte
        DictionaryDecode.put("\\",127); // consume 2 byte
        //DictionaryEncode.put(127, "\\u001B");
    }

    public static String toBinary(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for (int i = 0; i < Byte.SIZE * bytes.length; i++)
        {
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        }
        return sb.toString();
    }

    public static String toBinaryRadix(byte[] bytes, int radix)
    {
        int bitlength = bytes.length * Byte.SIZE;
        int modresultbyradix = bitlength % radix;
        int stringleft;
        if(modresultbyradix!=0)
        {
            stringleft = radix - modresultbyradix;
        }
        else
        {
            stringleft = 0;

        }

        StringBuilder sb = new StringBuilder((bytes.length + stringleft) * Byte.SIZE);
        for (int i = 0; i < Byte.SIZE * bytes.length; i++)
        {
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        }
        for (int i = 0; i < stringleft; i++)
        {
            sb.append('0');
        }

        return sb.toString();
    }

    public static byte[] fromBinary(String s)
    {
        int sLen = s.length();
        byte[] toReturn = new byte[(sLen + Byte.SIZE - 1) / Byte.SIZE];
        char c;
        for (int i = 0; i < sLen; i++)
        {
            if ((c = s.charAt(i)) == '1')
            {
                toReturn[i / Byte.SIZE] = (byte) (toReturn[i / Byte.SIZE] | (0x80 >>> (i % Byte.SIZE)));
            }
            else if (c != '0')
            {
                throw new IllegalArgumentException();
            }
        }
        return toReturn;
    }

    public static byte[] fromBinaryRadix(String s, int radix)
    {
        int originallen =s.length();

        int leftoverstring = originallen % radix;
        String substring = s.substring(0,originallen-leftoverstring);

        int sLen = substring.length();
        byte[] toReturn = new byte[(sLen + Byte.SIZE - 1) / Byte.SIZE];
        char c;
        for (int i = 0; i < sLen; i++)
        {
            if ((c = s.charAt(i)) == '1')
            {
                toReturn[i / Byte.SIZE] = (byte) (toReturn[i / Byte.SIZE] | (0x80 >>> (i % Byte.SIZE)));
            }
            else if (c != '0')
            {
                throw new IllegalArgumentException();
            }
        }
        return toReturn;
    }

    public static String Encode(byte[] bytes)
    {
        //TODO: DEBUGGING ONLY

        //return Cryptography.BytetoBase64String(bytes);
        String binarystring = toBinaryRadix(bytes, 7);
        int binarystringlength = binarystring.length();
        int numofcharacter = binarystringlength/7;
        StringBuilder sb = new StringBuilder(numofcharacter);
        int idx = 0;
        for(int i = 0; i<numofcharacter; i++)
        {
            String sub = binarystring.substring(idx, idx+7);
            String subreverse = new StringBuilder(sub).reverse().toString();
            int intfrombit = Integer.parseInt(subreverse, 2);
            String mapping = DictionaryEncode.get(intfrombit);
            //System.out.println("Sub: " + sub + " SubReverse: " + subreverse + " Integer: " + intfrombit + " Mapping: " + mapping);
            sb.append(mapping);
            idx=idx+7;
        }
        String encoded = sb.toString();
        String formatted = encoded.replace("/","//");
        String formatted2 = formatted.replace("\\","/?");
        String formatted3 = formatted2.replace("[","/(");
        String formatted4 = formatted3.replace("]","/)");
        return formatted4;
    }

    public static byte[] Decode (String text)
    {
        //TODO: DEBUGGING ONLY
        //return Cryptography.Base64StringtoByte(text);

        String PreProcess1 = text.replace("/)","]");
        String PreProcess2 = PreProcess1.replace("/(","[");
        String PreProcess3 = PreProcess2.replace("/?","\\");
        String PreProcess4 = PreProcess3.replace("//","/");

        int stringlength = PreProcess4.length();
        StringBuilder sb = new StringBuilder(stringlength*7);
        for(int i=0;i<stringlength;i++)
        {
            String sub = "" + PreProcess4.charAt(i);
            if(DictionaryDecode.containsKey(sub)) // Check whether the character is on table or not
            {
                int mapping = DictionaryDecode.get(sub);
                String binarystring = String.format("%7s", Integer.toBinaryString(mapping)).replace(' ', '0');
                //System.out.println("SubDecode: " + sub + " Mapping: " + mapping + " Binary: " + binarystring);
                String reversebinarystring = new StringBuilder(binarystring).reverse().toString();
                sb.append(reversebinarystring);
            }
            //System.out.println("SubDecode: " + sub);

        }
        String result = sb.toString();
        return fromBinaryRadix(result,8);
    }


}
