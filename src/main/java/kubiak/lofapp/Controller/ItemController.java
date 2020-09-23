package kubiak.lofapp.Controller;

import kubiak.lofapp.Model.Image;
import kubiak.lofapp.Model.Item;
import kubiak.lofapp.Model.User;
import kubiak.lofapp.Repositories.*;
import kubiak.lofapp.Service.AWSS3Service;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class ItemController {
    private ItemCategoryRepository itemCategoryRepository;
    private ItemRepository itemRepository;
    private ImageRepository imageRepository;
    private VoteRepository voteRepository;
    private UserRepository userRepository;
    private ItemTypeRepository itemTypeRepository;

    @Autowired
    private AWSS3Service service;

    public ItemController(ItemCategoryRepository itemCategoryRepository, ItemRepository itemRepository, ImageRepository imageRepository, VoteRepository voteRepository, UserRepository userRepository, ItemTypeRepository itemTypeRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemRepository = itemRepository;
        this.imageRepository = imageRepository;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.itemTypeRepository = itemTypeRepository;
    }
    /**
     * Method looks for all items of category chosen by user.
     *
     * @param id product category id
     * @param model
     * @return view of page shows all items of chosen category
     */
    @GetMapping("/items/all/{id}")
    public String showAll(@PathVariable("id") int id, Model model){
        List <Item> items = itemRepository.findByItemCategoryId(id);

        model.addAttribute("items", items);
        model.addAttribute("itemTypes",itemTypeRepository.findAll());
        return "items/all";
    }

    /**
     * Method shows details, images and number of votes of chosen item.
     * @param id item id
     * @param model
     * @return view of item details
     */
    @GetMapping("/items/details/{id}")
    public String showDetails(@PathVariable("id") int id, Model model) {
        Item item = itemRepository.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        int userId = user.getId();

        model.addAttribute("itemTypes",itemTypeRepository.findAll());
        model.addAttribute("detailedItems", item);
        model.addAttribute("itemImages", imageRepository.findByItemId(id));
        model.addAttribute("votes", voteRepository.findByUserIdAndItemId(userId,id));
        model.addAttribute("userId",userId);

        item.setViews(item.getViews()+1);
        itemRepository.save(item);
        return "items/details";
    }
    @GetMapping("items/choose-category")
    public String showChooseCategoryItemPage(Model model){
        System.out.println(itemTypeRepository.findById(0).getName());
        System.out.println(itemTypeRepository.findById(1).getName());
        model.addAttribute("itemTypes",itemTypeRepository.findAll());
        return "items/choose-category";
    }
    @GetMapping("items/add/{id}")
    public String showAddItemPage(@PathVariable("id") int itemTypeId, Model model){
        Item item = new Item();
        model.addAttribute("item",item);
        model.addAttribute("itemType",itemTypeRepository.findById(itemTypeId));
        model.addAttribute("itemCategories",itemCategoryRepository.findByItemTypeId(itemTypeId));
        model.addAttribute("itemTypes",itemTypeRepository.findAll());
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "items/add";
    }
    @PostMapping("items/add-new-item")
    public String addNewItem(@Valid Item item, BindingResult bindingResult, @RequestParam(value = "files", required = false) MultipartFile[] files, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        Date date = new Date();

        model.addAttribute("itemTypes",itemTypeRepository.findAll());
        model.addAttribute("topViewedItems", itemRepository.findTop10ByOrderByViewsDesc());
        model.addAttribute("newestItems", itemRepository.findTop10ByOrderByCreateDateDesc());

        // Date formatting according to the database
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh-mm");
        // Setting create date of item
        item.setCreateDate(formatter.format(date));

        List<MultipartFile> filesList = Arrays.asList(files);

        // Iteration for all files uploaded by user
        for (int i = 0; i < filesList.size(); i++) {
            MultipartFile file = filesList.get(i);
            Image image = new Image();
            // Filling data about image
            item.setUser(user);
            // Setting the first image as a thumbnail
            if(i==0){
                item.setImage(image);
            }
            image.setItem(item);
            itemRepository.save(item);
            // Setting image URL to AmazonS3
            imageRepository.save(image);
            image.setImageUrl("https://lofapp-uploads.s3.eu-west-2.amazonaws.com/" + image.getId() + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
            imageRepository.save(image);
            service.uploadFile(file, image.getId());
        }
        return "index";
    }
}

