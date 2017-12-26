package com.yellowman.tinwork.yourname.entity;

/**
 * Created by Marc Intha-amnouay on 26/12/2017.
 * Created by Didier Youn on 26/12/2017.
 * Created by Abdel-Atif Mabrouck on 26/12/2017.
 * Created by Antoine Renault on 26/12/2017.
 */

public class Image {
    private String fileName;
    private String resolution;
    private int id;

    /**
     *
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     *
     * @param filename
     */
    public void setFileName(String filename) {
        this.fileName = filename;
    }

    /**
     *
     * @return
     */
    public String getResolution() {
        return resolution;
    }

    /**
     *
     * @param resolution
     */
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
}
