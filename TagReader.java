import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
 
public class TagReader {
    File file; Reader reader; BufferedReader in;
    public ArrayList<String> lines = new ArrayList<String>();
   
    public TagReader(File file) {
        this.file = file;
       
        try {
            reader = new InputStreamReader(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        in = new BufferedReader(reader);
       
        try {
            read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
   
    private void read() throws IOException {
        String line;
       
        // read all the lines until the bottom of the file
        while ((line = in.readLine()) != null) {
            lines.add(line);
        }
    }
   
    public String getData(String tag) {
        return getData(tag, "");
    }
   
    public String getData(String tag, String defaultData) {
        for(int i=0; i<lines.size(); i++) {
            // check for the tag
            if(lines.get(i).startsWith("<" + tag + ">")) {
                // replace the tag bit with nothing so you're left with just the data
                String data = lines.get(i).replace("<" + tag + ">", "");
               
                // return the data
                return data;
            }
        }
       
        return defaultData;
    }
   
    public String[] getParameterData(String tag) {
        return getParameterData(tag, new String[]{});
    }
   
    public String[] getParameterData(String tag, String[] defaults) {
        for(int i=0; i<lines.size(); i++) {
            // check for the tag
            if(lines.get(i).startsWith("<" + tag + ">")) {
                // replace the tag bit with nothing so you're left with just the data
                String data = lines.get(i).replace("<" + tag + ">", "");
                String[] parameters = data.split("\\|");
               
                // return the data
                return parameters;
            }
        }
       
        return defaults;
    }
   
    public String getTagName(int index){
        String line = lines.get(index);
        // clean our tagName field up so that we can get data with it
        if(line.startsWith("<")){
            line = line.substring(0, line.indexOf(">"));
            line = line.replace("<", "");
            line = line.replace(">","");
        }
        return line;
    }
   
    public void addData(String tag, String data) throws IOException {
        FileWriter fw = new FileWriter(file.getAbsolutePath());
        BufferedWriter bw = new BufferedWriter(fw);
       
        String tagAndData = "<" + tag + ">" + data;
        bw.write(tagAndData + "\n");
        bw.close();
       
        // add it to our list of lines
        lines.add(tagAndData);
    }
   
    public void setData(String tag, String newData) throws IOException {
        for(int i=0; i<lines.size(); i++) {
            if(lines.get(i).contains(tag)) {
                String oldData = lines.get(i).replace("<" + tag + ">", "");
                lines.set(i, lines.get(i).replace(oldData, newData));
            }
        }
       
        // write all the lines
        FileWriter fw = new FileWriter(file.getAbsolutePath());
        BufferedWriter bw = new BufferedWriter(fw);
       
        for(int i=0; i<lines.size(); i++) {
            bw.write(lines.get(i) + "\n");
        }
        bw.close();
    }
}
