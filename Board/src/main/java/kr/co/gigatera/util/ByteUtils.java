package kr.co.gigatera.util;

public class ByteUtils {
	 
    //public static Byte DEFAULT_BYTE = new Byte((byte) 0);
 
    /**
     * ���ڿ��� ����Ʈ�� ��ȯ�Ѵ�.
     * 
     *  ByteUtils.toByte("1", *) = 0x01 ByteUtils.toByte("-1", *) = 0xff
     *  ByteUtils.toByte("a", 0x00) = 0x00 
     * 
     * @param value 10���� ���ڿ� ��
     * @param defaultValue
     * @return
     */
    public static byte toByte(String value, byte defaultValue) {
        try {
            return Byte.parseByte(value);
        }
        catch (Exception e) {
            return defaultValue;
        }
    }
 
    /**
     * ���ڿ��� ����Ʈ�� ��ȯ�Ѵ�.
     * 
     * ByteUtils.toByteObject("1", *) = 0x01 
     * ByteUtils.toByteObject("-1", *) = 0xff 
     * ByteUtils.toByteObject("a", 0x00) = 0x00  
     * 
     * @param value 10���� ���ڿ� ��
     * @param defaultValue
     * @return
     */
    public static Byte toByteObject(String value, Byte defaultValue) {
        try {
            return new Byte(value);
        }
        catch (Exception e) {
            return defaultValue;
        }
    }
 
    /**
     * singed byte�� unsinged byte�� ��ȯ�Ѵ�. 
     * Java���� unsinged Ÿ���� ���⶧����, 
     * int�� ��ȯ�Ѵ�.(b & 0xff)
     * 
     * @param b singed byte
     * @return unsinged byte
     */
    public static int unsignedByte(byte b) {
        return b & 0xFF;
    }
 
    /**
     * �Է��� ����Ʈ �迭(4����Ʈ)�� int ������ ��ȯ�Ѵ�.
     * 
     * @param src
     * @param srcPos
     * @return
     */
    public static int toInt(byte[] src, int srcPos) {
        int dword = 0;
        for (int i = 0; i < 4; i++) {
            dword = (dword << 8) + (src[i + srcPos] & 0xFF);
        }
        return dword;
    }
 
    /**
     * �Է��� ����Ʈ �迭(4����Ʈ)�� int ������ ��ȯ�Ѵ�.
     * 
     * @param src
     * @return
     */
    public static int toInt(byte[] src) {
        return toInt(src, 0);
    }
 
    /**
     * �Է��� ����Ʈ �迭(8����Ʈ)�� long ������ ��ȯ�Ѵ�.
     * 
     * @param src
     * @param srcPos
     * @return
     */
    public static long toLong(byte[] src, int srcPos) {
        long qword = 0;
        for (int i = 0; i < 8; i++) {
            qword = (qword << 8) + (src[i + srcPos] & 0xFF);
        }
        return qword;
    }
 
    /**
     * �Է��� ����Ʈ �迭(8����Ʈ)�� long ������ ��ȯ�Ѵ�.
     * 
     * @param src
     * @return
     */
    public static long toLong(byte[] src) {
        return toLong(src, 0);
    }
 
    /**
     * int ���� ���� ����Ʈ �迭(4����Ʈ)�� ��ȯ�Ѵ�.
     * 
     * @param value
     * @param dest
     * @param destPos
     */
    public static void toBytes(int value, byte[] dest, int destPos) {
        for (int i = 0; i < 4; i++) {
            dest[i + destPos] = (byte) (value >> ((7 - i) * 8));
        }
    }
 
    /**
     * int ���� ���� ����Ʈ �迭(4����Ʈ)�� ��ȯ�Ѵ�.
     * 
     * @param value
     * @return
     */
    public static byte[] toBytes(int value) {
        byte[] dest = new byte[4];
        toBytes(value, dest, 0);
        return dest;
    }
 
    /**
     * long ���� ���� ����Ʈ �迭(8����Ʈ)�� ��ȯ�Ѵ�.
     * 
     * @param value
     * @param dest
     * @param destPos
     */
    public static void toBytes(long value, byte[] dest, int destPos) {
        for (int i = 0; i < 8; i++) {
            dest[i + destPos] = (byte) (value >> ((7 - i) * 8));
        }
    }
    
    
    
    /**
     * long ���� ���� ����Ʈ �迭(8����Ʈ)�� ��ȯ�Ѵ�.
     * 
     * @param value
     * @return
     */
    public static byte[] toBytes(long value) {
        byte[] dest = new byte[8];
        toBytes(value, dest, 0);
        return dest;
    }
 
    /**
     * 8, 10, 16���� ���ڿ��� ����Ʈ �迭�� ��ȯ�Ѵ�. 
     * 8, 10������ ���� ���ڿ��� 3�ڸ���, 16������
     * ���� 2�ڸ���, �ϳ��� byte�� �ٲ��.
     * 
     * ByteUtils.toBytes(null) = null
     * ByteUtils.toBytes("0E1F4E", 16) = [0x0e, 0xf4, 0x4e] 
     * ByteUtils.toBytes("48414e", 16) = [0x48, 0x41, 0x4e]
     * 
     * 
     * @param digits ���ڿ�
     * @param radix ����(8, 10, 16�� ����)
     * @return
     * @throws NumberFormatException
     */
    public static byte[] toBytes(String digits, int radix) throws IllegalArgumentException, NumberFormatException {
        if (digits == null) { return null; }
        if (radix != 16 && radix != 10 && radix != 8) { throw new IllegalArgumentException("For input radix: \"" + radix + "\""); }
        int divLen = (radix == 16) ? 2 : 3;
        int length = digits.length();
        if (length % divLen == 1) { throw new IllegalArgumentException("For input string: \"" + digits + "\""); }
        length = length / divLen;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            int index = i * divLen;
            bytes[i] = (byte) (Short.parseShort(digits.substring(index, index + divLen), radix));
        }
        return bytes;
    }
 
    /**
     * 16���� ���ڿ��� ����Ʈ �迭�� ��ȯ�Ѵ�. 
     * ���ڿ��� 2�ڸ��� �ϳ��� byte�� �ٲ��.
     * 
     * ByteUtils.hexStringToBytes(null) = null
     * ByteUtils.hexStringToBytes("0E1F4E") = [0x0e, 0xf4, 0x4e]
     * ByteUtils.hexStringToBytes("48414e") = [0x48, 0x41, 0x4e] 
     * 
     * @param digits 16���� ���ڿ�
     * @return
     * @throws NumberFormatException
     * @see HexUtils.toBytes(String)
     */
    public static byte[] hexStringToBytes(String digits) throws IllegalArgumentException, NumberFormatException {
        if (digits == null) { return null; }
        int length = digits.length();
        if (length % 2 == 1) { throw new IllegalArgumentException("For input string: \"" + digits + "\""); }
        length = length / 2;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            int index = i * 2;
            bytes[i] = (byte) (Short.parseShort(digits.substring(index, index + 2), 16));
        }
        return bytes;
    }
 
    /**
     * unsigned byte(����Ʈ)�� 16���� ���ڿ��� �ٲ۴�.
     * 
     * ByteUtils.toHexString((byte)1) = "01" 
     * ByteUtils.toHexString((byte)255) = "ff"
     * 
     * @param b unsigned byte
     * @return
     * @see HexUtils.toString(byte)
     */
    public static String toHexString(byte b) {
        StringBuffer result = new StringBuffer(3);
        result.append(Integer.toString((b & 0xF0) >> 4, 16));
        result.append(Integer.toString(b & 0x0F, 16));
        return result.toString();
    }
 
    /**
     * unsigned byte(����Ʈ) �迭�� 16���� ���ڿ��� �ٲ۴�.
     * 
     * ByteUtils.toHexString(null) = null
     * ByteUtils.toHexString([(byte)1, (byte)255]) = "01ff" 
     * 
     * @param bytes unsigned byte's array
     * @return
     * @see HexUtils.toString(byte[])
     */
    public static String toHexString(byte[] bytes) {
        if (bytes == null) { return null; }
 
        StringBuffer result = new StringBuffer();
        for (byte b : bytes) {
            result.append(Integer.toString((b & 0xF0) >> 4, 16));
            result.append(Integer.toString(b & 0x0F, 16));
        }
        return result.toString();
    }
 
    /**
     * �� �迭�� ���� �������� ���Ѵ�.
     * 
     * ArrayUtils.equals(null, null) = true
     * ArrayUtils.equals(["one", "two"], ["one", "two"]) = true 
     * ArrayUtils.equals(["one", "two"], ["three", "four"]) = false
     * 
     * @param array1
     * @param array2
     * @return �����ϸ� true, �ƴϸ� false
     */
    public static boolean equals(byte[] array1, byte[] array2) {
        if (array1 == array2) { return true; }
 
        if (array1 == null || array2 == null) { return false; }
 
        if (array1.length != array2.length) { return false; }
 
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) { return false; }
        }
 
        return true;
    }
}

