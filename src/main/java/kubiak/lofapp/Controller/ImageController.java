package kubiak.lofapp.Controller;

import kubiak.lofapp.Model.Image;
import kubiak.lofapp.Repositories.ImageRepository;
import kubiak.lofapp.Service.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageController {
    private ImageRepository imageRepository;

    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Method looks for image by id in database.
     *
     * @param id - image id
     * @return url of image
     */
    @GetMapping("/image/{id}")
    public String getImage(@PathVariable("id") int id){
        Image image = imageRepository.findById(id);
        return "redirect:" + image.getImageUrl();
    }

}
