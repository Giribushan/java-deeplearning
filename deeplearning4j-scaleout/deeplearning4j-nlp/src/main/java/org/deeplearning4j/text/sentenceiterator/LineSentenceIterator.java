package org.deeplearning4j.text.sentenceiterator;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import java.io.*;

/**
 * Each line is a sentence
 *
 * @author Adam Gibson
 */
public class LineSentenceIterator extends BaseSentenceIterator {

    private InputStream file;
    private LineIterator iter;
    private File f;



    public LineSentenceIterator(File f) {
        if (!f.exists() || !f.isFile())
            throw new IllegalArgumentException("Please specify an existing file");
        try {
            this.f = f;
            this.file = new BufferedInputStream(new FileInputStream(f));
            iter = IOUtils.lineIterator(this.file,"UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String nextSentence() {
        String line = iter.nextLine();
        if (preProcessor != null) {
            line = preProcessor.preProcess(line);
        }
        return line;
    }

    @Override
    public boolean hasNext() {
        return iter.hasNext();
    }

    @Override
    public void reset() {
        try {
            if(file != null)
                file.close();
            if(iter != null)
                iter.close();
            this.file = new BufferedInputStream(new FileInputStream(f));
            iter = IOUtils.lineIterator(this.file,"UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}