package kubiak.lofapp.Controller;

import kubiak.lofapp.Model.Item;
import kubiak.lofapp.Model.User;
import kubiak.lofapp.Model.Vote;
import kubiak.lofapp.Repositories.ItemRepository;
import kubiak.lofapp.Repositories.UserRepository;
import kubiak.lofapp.Repositories.VoteRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class VoteController {
    VoteRepository voteRepository;
    UserRepository userRepository;
    ItemRepository itemRepository;

    public VoteController(VoteRepository voteRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping("/items/vote")
    public String vote(@RequestParam int itemId, int userId, boolean choice){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        Item item = itemRepository.findById(itemId);
        int userPoints = user.getPoints();

        //  User can vote only once on item, so there is looking for other votes
        if(voteRepository.findByUserIdAndItemId(userId, itemId) == null){
            Vote vote = new Vote(item, user, choice);
            voteRepository.save(vote);

            if(!choice){
                item.setFakeVotes(item.getFakeVotes()+1);
                item.setFakePoints(item.getFakePoints()+userPoints);
            }
            if(choice){
                item.setOriginalVotes(item.getOriginalVotes()+1);
                item.setOriginalPoints(item.getOriginalPoints()+userPoints);
            }
            itemRepository.save(item);
        }

        return "redirect:/items/details/"+itemId;
    }
}
