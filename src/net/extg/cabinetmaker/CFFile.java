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

import java.util.Calendar;
import java.util.Vector;

/**
 *
 * @author Graham Rivers-Brown
 */
public class CFFile {

    private int cbFile;             /* uncompressed size of this file in bytes */
    private int uoffFolderStart;    /* uncompressed offset of this file in the folder */
    private int iFolder;            /* index into the CFFOLDER area */
    private int date;               /* date stamp for this file */
    private int time;               /* time stamp for this file */
    private int attribs;            /* attribute flags for this file */
    private int[] szName;           /* name of this file */

    
    public CFFile(String filename, int size, int offset, int folderNumber)
    {
        Calendar c = Calendar.getInstance();
        
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        
        date = ((year - 1980) << 9) + (month << 5) + (day);
        time = (hours << 11) + (minutes << 5) + (seconds/2);
        
        attribs = 0x80; //UTF encoding for filename string
        
        setFilename(filename);
        cbFile = size;
        uoffFolderStart = offset;
        iFolder = folderNumber;
    }
    
    private void setFilename(String filename)
    {
        szName = new int[filename.length() + 1];
        for (int i = 0; i < filename.length(); i++)
        {
            szName[i] = filename.charAt(i);
        }
        szName[filename.length()] = 0x00;
    }
    
    public Vector<Byte> makeByteArray()
    {
        Vector<Byte> b = new Vector<Byte>();
        
        b.addAll(convertToByte(cbFile, 4));
        b.addAll(convertToByte(uoffFolderStart, 4));
        b.addAll(convertToByte(iFolder, 2));
        b.addAll(convertToByte(date, 2));
        b.addAll(convertToByte(time, 2));
        b.addAll(convertToByte(attribs, 2));
        for (int i = 0 ; i < szName.length; i++)
        {
            b.addAll(convertToByte(szName[i], 1));
        }
                
        return b;
    }
    
    private Vector<Byte> convertToByte(int val, int numBytes)
    {
        Vector<Byte> b = new Vector<Byte>();
        Integer tempInt;
        Byte byteToAdd;
        if (numBytes == 1)
        {
            tempInt = new Integer(val);
            byteToAdd = new Byte(tempInt.byteValue());
            b.add(byteToAdd);
        }
        else if (numBytes == 2)
        {
            tempInt = new Integer(0xFF & val);
            byteToAdd = new Byte(tempInt.byteValue());
            b.add(byteToAdd);
            tempInt = new Integer((0xFF00 & val) >>> 8);
            byteToAdd = new Byte(tempInt.byteValue());
            b.add(byteToAdd);
        }
        else if (numBytes == 3)
        {
            tempInt = new Integer(0xFF & val);
            byteToAdd = new Byte(tempInt.byteValue());
            b.add(byteToAdd);
            tempInt = new Integer((0xFF00 & val) >>> 8);
            byteToAdd = new Byte(tempInt.byteValue());
            b.add(byteToAdd);
            tempInt = new Integer((0xFF0000 & val) >>> 16);
            byteToAdd = new Byte(tempInt.byteValue());
            b.add(byteToAdd);
        }
        else if (numBytes == 4)
        {
            tempInt = new Integer(0xFF & val);
            byteToAdd = new Byte(tempInt.byteValue());
            b.add(byteToAdd);
            tempInt = new Integer((0xFF00 & val) >>> 8);
            byteToAdd = new Byte(tempInt.byteValue());
            b.add(byteToAdd);
            tempInt = new Integer((0xFF0000 & val) >>> 16);
            byteToAdd = new Byte(tempInt.byteValue());
            b.add(byteToAdd);
            tempInt = new Integer((0xFF000000 & val) >>> 24);
            byteToAdd = new Byte(tempInt.byteValue());
            b.add(byteToAdd);
        }
        else
        {
            b.add(new Byte("255"));
        }
        
        return b;
    }
}
