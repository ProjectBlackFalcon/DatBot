package utils.d2p.informations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class D2pFileManager {
	
    private List<D2PFileDlm> ListD2pFileDlm = new ArrayList<D2PFileDlm>();
    
    public D2pFileManager(String path) throws IOException
    {
        String file = null;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (File File_loopVariable : listOfFiles)
		{
            file = File_loopVariable.getAbsolutePath();
            if (getFileExtension(File_loopVariable).toUpperCase().equals("D2P")){
                this.ListD2pFileDlm.add(new D2PFileDlm(file));
            }
		}
    }
    
    public byte[] GetMapBytes(String name) throws IOException
    {
		D2PFileDlm dlm = null;
    	for(int i = 0 ; i < this.ListD2pFileDlm.size() ; i++){
    		if(this.ListD2pFileDlm.get(i).ExistsDlm(name)){
    			dlm = this.ListD2pFileDlm.get(i); 
    		}
    	}
        return dlm.ReadFile(name);
    }
    
    private String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

	public List<D2PFileDlm> getListD2pFileDlm()
	{
		return ListD2pFileDlm;
	}
    

}
