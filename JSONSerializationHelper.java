import com.fasterxml.jackson.databind.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class JSONSerializationHelper{

    public static void serialiseToFile(Object object,String filePath)throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        FileOutputStream fos = new FileOutputStream(filePath);
        objectMapper.writeValue(fos, object);
        fos.flush();
        fos.close();
    }


    public static <T> T deserializeFromFile(String filePath, Class<T> clazz)throws IOException, ClassCastException, ClassNotFoundException{
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filePath);
        return (T) objectMapper.readValue(file, clazz);
    }
}
