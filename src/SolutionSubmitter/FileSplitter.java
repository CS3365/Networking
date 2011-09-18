/*
 *  The MIT License
 * 
 *  Copyright 2011 Mike Kent.
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 * 
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 * 
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package SolutionSubmitter;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Mike Kent
 */
public class FileSplitter {

	private FileInfo info;
	private File file;
	private int numParts, curPart;
	private long curLocation;
	private DataInputStream in;

	public FileSplitter(FileInfo info) throws FileNotFoundException {
		this.info = info;
		file = new File(info.getSourcePath());
		if(!file.exists()) {
			throw new FileNotFoundException();
		}
		numParts = (int)file.length()/FilePart.PART_SIZE;
		curPart = 0;
		curLocation = 0;
		in = new DataInputStream(new FileInputStream(file));
	}

	public FilePart getNextPart() throws IOException {
		if(isEnd()) {
			throw new IOException("Already at end of file");
		}
		FilePart part = new FilePart(info, curLocation, curPart++, numParts);
		//set data length and read data into part.data
		part.setDataLength(in.read(part.getData()));
		curLocation += FilePart.PART_SIZE;
		return part;
	}

	public boolean isEnd() {
		return curLocation >= file.length();
	}
}
