package kubiak.lofapp.Service;

import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Service {

    void uploadFile(MultipartFile multipartFile, int imageId);
    void getFile(String fileName);
}
