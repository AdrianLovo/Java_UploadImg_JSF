/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Adrian
 */
@ManagedBean
@RequestScoped
public class imagenController2 {

    private UploadedFile  file;
    private String url;
    private int limite = 100000;    //0.1MB
    
    public imagenController2() {
    }
    
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
     
    public void upload() {
        url = System.getProperty("user.dir");   //Ruta donde corre la aplicacion C:\Users\Adrian\AppData\Roaming\NetBeans\8.0.2\config\GF_4.1\domain1\config
        url = url + "\\images\\";           //Carpeta images en la ruta
        
        if(file.getSize() < limite){
            if(file != null) {
                try {
                    copyFile(file.getFileName(), file.getInputstream());
                    FacesMessage message = new FacesMessage("Success", file.getFileName() + "  is uploaded");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                } catch (IOException e) {
                    e.printStackTrace();
                }            
            }
        }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR , "Invalid file size" + file.getSize() ,null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
        
    }
    
     public void copyFile(String fileName, InputStream in) {
        try {
            OutputStream out = new FileOutputStream(new File(url + fileName));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
            Date date = new Date();
            String ruta1 = url + fileName;
            String ruta2 = url + dateFormat.format(date)+"-"+fileName;
            //System.out.println("Archivo: "+ruta1+" Renombrado a: "+ruta2);           
            File archivo=new File(ruta1);
            archivo.renameTo(new File(ruta2));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
}
