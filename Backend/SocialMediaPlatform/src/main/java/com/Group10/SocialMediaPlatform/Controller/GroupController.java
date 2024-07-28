package com.Group10.SocialMediaPlatform.Controller;

import com.Group10.SocialMediaPlatform.Repository.UserRepository;
import com.Group10.SocialMediaPlatform.Service.GroupService;
import com.Group10.SocialMediaPlatform.model.Group;
import com.Group10.SocialMediaPlatform.model.GroupMember;
import com.Group10.SocialMediaPlatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserRepository userRepository;

    // Endpoint to create a new group
    @PostMapping("/create/{creatorId}")
    public Group createGroup(@RequestBody Group group, @PathVariable Integer creatorId) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        group.setCreatedBy(creator);
        return groupService.createGroup(group);
    }

    // Endpoint to join a public group
    @PostMapping("/{groupId}/join")
    public String joinPublicGroup(@PathVariable Integer groupId, @RequestParam Integer userId) {
        groupService.joinPublicGroup(groupId, userId);
        return "Successfully joined";
    }

    // Endpoint to update the type of a group (public/private)
    @PutMapping("/{groupId}/updateType")
    public Group updateGroupType(@PathVariable Integer groupId, @RequestBody Map<String, Boolean> request) {
        Boolean isPrivate = request.get("isPrivate");
        return groupService.updateGroupType(groupId, isPrivate);
    }

    // Endpoint to get group details by group ID
    @GetMapping("/{groupId}")
    public Group getGroupById(@PathVariable Integer groupId) {
        return groupService.getGroupByGroupId(groupId);
    }

    // Endpoint to get members of a group by group ID
    @GetMapping("/{groupId}/members")
    public List<GroupMember> getGroupMembersByGroupId(@PathVariable Integer groupId) {
        return groupService.getGroupMembersByGroupId(groupId);
    }

    // Endpoint to remove a member from a group
    @DeleteMapping("/{groupId}/members/delete/{userId}")
    public String removeMemberFromCurrGroup(@PathVariable Integer groupId, @PathVariable Integer userId) {
        groupService.removeMemberFromCurrGroup(groupId, userId);
        return "Successfully deleted";
    }

    // Endpoint to get all groups
    @GetMapping("/all")
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    // Endpoint to get groups a user belongs to
    @GetMapping("/user/{userId}")
    public List<Group> getMyGroups(@PathVariable Integer userId) {
        return groupService.getMyGroups(userId);
    }

    // Endpoint to update the interests of a group
    @PutMapping("/{groupId}/interests")
    public String updateInterests(@PathVariable Integer groupId, @RequestBody Map<String, String> request) {
        String interests = request.get("interests");
        Group updatedGroup = groupService.updateInterests(groupId, interests);
        return updatedGroup.getInterests();
    }
}
