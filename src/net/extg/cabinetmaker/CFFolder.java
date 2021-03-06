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

import java.util.Vector;

/**
 *
 * @author Graham Rivers-Brown
 */
public class CFFolder {

    public static final int NO_COMPRESSION = 0;
    
    private int coffCabStart;	/* offset of the first CFDATA block in this folder */
    private int cCFData;	/* number of CFDATA blocks in this folder */
    private int typeCompress;	/* compression type indicator */
    /*private int abReserve[];	/* (optional) per-folder reserved area */

    
    public CFFolder()
    {
        typeCompress = CFFolder.NO_COMPRESSION;
    }
    
    public void setCoffCabStart(int coffCabStart)
    {
        this.coffCabStart = coffCabStart;
    }
    
    public void setCCFData(int cCFData)
    {
        this.cCFData = cCFData;
    }
    
    public Vector<Byte> makeByteArray()
    {
        Vector<Byte> b = new Vector<Byte>();
        
        b.addAll(convertToByte(coffCabStart, 4));
        b.addAll(convertToByte(cCFData, 2));
        b.addAll(convertToByte(typeCompress, 2));
                
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
