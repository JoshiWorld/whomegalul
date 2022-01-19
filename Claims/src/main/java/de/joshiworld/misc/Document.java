package de.joshiworld.misc;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Location;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JoshiWorld
 */
public class Document {
    
    private static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().disableHtmlEscaping().create();
    private static final JSONParser parser = new JSONParser();
    
    private final JSONObject json;
    
    public Document() {
        json = new JSONObject();
    }

    public Document(JSONObject json) {
        this.json = json;
    }

    //<editor-fold defaultstate="collapsed" desc="append">
    public Document append(String key, JSONObject value) {
        this.json.put(key, value);
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="append">
    public Document append(String key, String value) {
        this.json.put(key, value);
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="append">
    public Document append(String key, Number value) {
        this.json.put(key, value);
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="append">
    public Document append(String key, Boolean value) {
        this.json.put(key, value);
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="append">
    public Document append(String key, Object value) {
        this.json.put(key, value);
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="append">
    public Document append(String key, List value) {
        this.json.put(key, value);
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="append">
    public Document append(String key, Location value) {
        JSONObject object = new JSONObject();
        object.put("x", value.getX());
        object.put("y", value.getY());
        object.put("z", value.getZ());
        object.put("yaw", value.getYaw());
        object.put("pitch", value.getPitch());
        object.put("world", value.getWorld().getName());
        append(key, object);
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="appendValues">
    public Document appendValues(Map<String, Object> values) {
        values.entrySet().forEach((value) -> {
            append(value.getKey(), value.getValue());
        });
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get">
    public Object get(String key) {
        return this.json.get(key);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getString">
    public String getString(String key) {
        return (String) get(key);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getLong">
    public long getLong(String key) {
        return (long) get(key);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getInt">
    public int getInt(String key) {
        return (int) getLong(key);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getDouble">
    public double getDouble(String key) {
        return (double) get(key);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getFloat">
    public float getFloat(String key) {
        return (float) getDouble(key);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getBoolean">
    public boolean getBoolean(String key) {
        return (boolean) get(key);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getMap">
    public Map getMap(String key) {
        return (Map) get(key);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getList">
    public List getList(String key) {
        return (List) get(key);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getLocation">
    public Location getLocation(String key) {
        try {
            return Location.deserialize((Map) get(key));
        } catch (Exception e) {
            Logger.getLogger(Document.class.getName()).log(Level.INFO, "Path {0}", key);
            return null;
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="removeKey">
    public Document removeKey(String key) {
        this.json.remove(key);
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="save">
    public boolean save(File file) {
        try {
            Files.createParentDirs(file);
            
            if (file == null)
                return false;
            if (file.exists())
                file.delete();
            
            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")) {
                gson.toJson(json, (writer));
                return true;
            } catch (IOException ex) {
                Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="loadDocument">
    public static Document loadDocument(File file) {
        try {
            JSONObject json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            return new Document(json);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Document();
    }
    //</editor-fold>
}
