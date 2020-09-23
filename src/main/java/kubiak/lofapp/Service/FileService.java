/** package kubiak.lofapp.Service;

import kubiak.lofapp.Exceptions.FileStorageException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    @Value("${app.upload.dir}")
    public String uploadDir;
    private String fileType;
    private String newFileName;

    public void uploadFile(MultipartFile file, int imageId){
        fileType = FilenameUtils.getExtension(file.getOriginalFilename());
        newFileName = imageId + "." + fileType;

        try{
            Path copyLocation = Paths
                    .get( uploadDir + File.separator + StringUtils.cleanPath(newFileName));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e){
            e.printStackTrace();
            throw new FileStorageException("Nie można dodać obrazu " + file.getOriginalFilename() + ". Spróbuj ponownie!");
        }
    }
}*/
