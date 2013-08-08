/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Graham Rivers-Brown
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package net.extg.cabinetmaker;

import java.io.*;
import java.util.Vector;

public class CabinetMakerExample {
	
	public static final String FILENAME_1 = "file1.txt";
	public static final String FILENAME_2 = "file2.txt";
	public static final String FILENAME_3 = "file3.txt";
	public static final String FILENAME_4 = "file4.txt";
	
    public static void main(String[] args) throws Exception {
        File f1 = new File(FILENAME_1);
        FileInputStream is1 = new FileInputStream(f1);
        long length1 = f1.length();
        
        File f2 = new File(FILENAME_2);
        FileInputStream is2 = new FileInputStream(f2);
        long length2 = f2.length();
        
        File f3 = new File(FILENAME_3);
        FileInputStream is3 = new FileInputStream(f3);
        long length3 = f3.length();
        
        byte[] file1 = readBytesFromFile(is1, length1);
        byte[] file2 = readBytesFromFile(is2, length2);
        byte[] file3 = readBytesFromFile(is3, length3);

        Vector<String> filenames = new Vector<String>();
        Vector<byte[]> files = new Vector<byte[]>();
        filenames.add(FILENAME_1);
        filenames.add(FILENAME_2);
        filenames.add(FILENAME_3);
        files.add(file1);
        files.add(file2);
        files.add(file3);
        
        CabFile c = new CabFile(filenames, files);

        FileOutputStream fs = new FileOutputStream(new File("out.cab"));
        fs.write(c.getCabFile());
        fs.close();
    }
    
    private static byte[] readBytesFromFile(FileInputStream is, long length) throws IOException
    {
        byte[] bytes = new byte[(int)length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        is.close();
        return bytes;
    }
}
