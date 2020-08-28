package cn.com.cgh.config.solr;

import java.io.Serializable;

/**
 * <p> TODO
 * @author Haidar
 * @date 2020/8/24 15:33
 **/
public class FileConfig implements Serializable {
    private static final long serialVersionUID = 5516075349620653480L;
    class Word implements Serializable {
        private static final long serialVersionUID = 5516075349620653480L;
        private String author;
        private String title;
        private String text;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
    private String file;
    private String fileSize;
    private String fileDir;
    private String fileLastModified;
    private String fileAbsolutePath;
    private Word word;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileLastModified() {
        return fileLastModified;
    }

    public void setFileLastModified(String fileLastModified) {
        this.fileLastModified = fileLastModified;
    }

    public String getFileAbsolutePath() {
        return fileAbsolutePath;
    }

    public void setFileAbsolutePath(String fileAbsolutePath) {
        this.fileAbsolutePath = fileAbsolutePath;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }
}
