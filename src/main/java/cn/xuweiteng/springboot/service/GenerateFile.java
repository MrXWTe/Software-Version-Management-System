package cn.xuweiteng.springboot.service;

public class GenerateFile {
    public native void generateFile(char[] modelNumberChar);
    public native String tdecrypto(int[] d, int[] n, char[] textChar);
    public native String tencrypto(int[] e, int[] n, char[] textChar);
}
