package entities;

import files.FileManager;
import files.FileTools;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Maxime
 */
public class Visit {

    private ArrayList<InterestPoint> IP;
    private Overview overview;
    private Info info;
    private String name;

    public Visit(String pathFrom, String name) {
    	if(!FileTools.Exist(new File(pathFrom)))
            FileTools.CreateDirectory(pathFrom);
    	
        initVisit(pathFrom);

        this.setIP(new ArrayList<InterestPoint>());
        this.setOverview(new Overview(pathFrom + "/" + FileManager.OVERVIEW_FOLDER));
        this.setInfo(new Info(pathFrom + "/" + FileManager.INFO_FOLDER));
        this.name = name;
    }

    private void initVisit(String pathFrom) {
        if (!FileTools.Exist(new File(pathFrom + "/" + FileManager.OVERVIEW_FOLDER)))
            FileTools.CreateDirectory(pathFrom + "/" + FileManager.OVERVIEW_FOLDER);
        if (!FileTools.Exist(new File(pathFrom + "/" + FileManager.INFO_FOLDER)))
            FileTools.CreateDirectory(pathFrom + "/" + FileManager.INFO_FOLDER);
    }

    /**
     * @return the iP
     */
    public ArrayList<InterestPoint> getIP() {
        return IP;
    }

    /**
     * @param iP the iP to set
     */
    public void setIP(ArrayList<InterestPoint> IP) {
        this.IP = IP;
    }

    public Overview getOverview() {
        return overview;
    }

    public void setOverview(Overview overview) {
        this.overview = overview;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Delete visit content
    public void delete(String pathFrom) {
        FileTools.Delete(pathFrom);
    }
    
    // Delete an IP to a visit
    public void deleteInterestPoint(InterestPoint IP, String pathFrom) {
        this.IP.remove(IP);
        IP.delete(pathFrom);
    }
    
    // Add an interest point to a visit
    public void addInterestPoint(InterestPoint IP) {
        this.IP.add(IP);
    }
}
