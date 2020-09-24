package kubiak.lofapp.Service;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class AWSServiceImplement implements AWSS3Service {
    @Autowired
    private AmazonS3 amazonS3;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    private String fileType;
    private String newFileName;

    private void uploadFileTos3bucket(String fileName, File file) {
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }
    private File convertMultiPartToFile(MultipartFile file, int imageId) throws IOException {
        fileType = FilenameUtils.getExtension(file.getOriginalFilename());
        newFileName = imageId + "." + fileType;

        File convFile = new File(newFileName);
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
    public void uploadFile(MultipartFile multipartFile, int imageId) {
        fileType = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        newFileName = imageId + "." + fileType;

        try {
            File file = convertMultiPartToFile(multipartFile, imageId);
            String fileName = newFileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getFile(String fileName){
        amazonS3.getObject(new GetObjectRequest(bucketName,fileName));
    }
}
