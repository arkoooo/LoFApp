package kubiak.lofapp.Controller;

import kubiak.lofapp.Model.Configuration;
import kubiak.lofapp.Model.Item;
import kubiak.lofapp.Model.User;
import kubiak.lofapp.Model.Vote;
import kubiak.lofapp.Repositories.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VoteController {
    VoteRepository voteRepository;
    UserRepository userRepository;
    ItemRepository itemRepository;
    RoleRepository roleRepository;
    ConfigurationRepository configurationRepository;
    Configuration configuration;
    int numberOfPointsToMarkItem;

    public VoteController(VoteRepository voteRepository, UserRepository userRepository, ItemRepository itemRepository, RoleRepository roleRepository, ConfigurationRepository configurationRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.roleRepository = roleRepository;
        this.configurationRepository = configurationRepository;
    }

    @GetMapping("/items/vote")
    public String vote(@RequestParam int itemId, int userId, boolean choice){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        Item item = itemRepository.findById(itemId);
        if(configuration == null){
            numberOfPointsToMarkItem = 10;
        }else{
            configuration = configurationRepository.findByOption("pointsToMarkItem");
            numberOfPointsToMarkItem = configuration.getValue();
        }


        //  User can vote only once on item, so there is looking for other votes
        if(voteRepository.findByUserIdAndItemId(userId, itemId) == null){
            if(checkIsUserAbleToVote(user)) {
                Vote vote = new Vote(item, user, choice, true);
                voteRepository.save(vote);
                // If user has role "tester", then add points
                addPointsToItem(item, user, choice);
            }else{
                Vote vote = new Vote(item, user, choice, false);
                voteRepository.save(vote);
            }
            itemRepository.save(item);
        }

        if(checkVotes(item,numberOfPointsToMarkItem)){
            addPointsToUsers(item);
        }
        promoteUser(user, numberOfPointsToMarkItem);
        return "redirect:/items/details/"+itemId;
    }
    // This method checks if User role is "tester"
    private boolean checkIsUserAbleToVote(User user){
        return user.getRole() == roleRepository.findByRole("TESTER");
    }
    // This method checks when an item reaches the genuine score
    private boolean checkVotes(Item item, int numberOfPointsToMarkItem){
        return item.getFakePoints() > numberOfPointsToMarkItem || item.getOriginalPoints() > numberOfPointsToMarkItem;
    }
    private void addPointsToUsers(Item item){
        List<Vote> votes = voteRepository.findByItemId(item.getId());
        boolean legit = legit(item);
        User user;

        // Loop checks if the user's voice coincides with those of others
        for(Vote vote : votes){
            // If item is marked as legit and user voted that is legit or item is marked as fake and user voted for fake
            // and vote wasn't summed up - then add points to account

            if(((legit && vote.isVote()) || (!legit && !vote.isVote())) && !vote.isSummedUp()){
                user = vote.getUser();
                user.setPoints(user.getPoints()+1);
                userRepository.save(user);
                vote.setSummedUp(true);
                voteRepository.save(vote);
            }
        }
    }
    // Add vote points to item
    private void addPointsToItem(Item item, User user, boolean choice){
        int userPoints = user.getPoints();
        if(!choice){
            item.setFakeVotes(item.getFakeVotes()+1);
            item.setFakePoints(item.getFakePoints()+userPoints);
        }
        if(choice){
            item.setOriginalVotes(item.getOriginalVotes()+1);
            item.setOriginalPoints(item.getOriginalPoints()+userPoints);
        }
    }
    private boolean legit(Item item){
        return item.getOriginalPoints() > item.getFakePoints();
    }
    private void promoteUser(User user, int points){
        if(user.getPoints() >= points){
            user.setRole(roleRepository.findByRole("TESTER"));
        }
    }
}
