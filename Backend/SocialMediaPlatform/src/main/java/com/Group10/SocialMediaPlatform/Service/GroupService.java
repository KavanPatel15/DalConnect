package com.Group10.SocialMediaPlatform.Service;

import com.Group10.SocialMediaPlatform.Repository.GroupMemberRepository;
import com.Group10.SocialMediaPlatform.Repository.GroupRepository;
import com.Group10.SocialMediaPlatform.Repository.UserRepository;
import com.Group10.SocialMediaPlatform.model.Group;
import com.Group10.SocialMediaPlatform.model.GroupMember;
import com.Group10.SocialMediaPlatform.model.GroupMemberId;
import com.Group10.SocialMediaPlatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    // Creates a new group and assigns the creator as an admin
    public Group createGroup(Group group) {
        if (group.getCreatedBy() == null) {
            throw new IllegalArgumentException("Group must have a creator");
        }

        Group savedGroup = groupRepository.save(group);

        GroupMemberId groupMemberId = new GroupMemberId(savedGroup.getGroupId(), savedGroup.getCreatedBy().getUserId());
        GroupMember creatorAdmin = new GroupMember();
        creatorAdmin.setId(groupMemberId);
        creatorAdmin.setGroup(savedGroup);
        creatorAdmin.setRole("admin");
        creatorAdmin.setUser(savedGroup.getCreatedBy());
        groupMemberRepository.save(creatorAdmin);

        return savedGroup;
    }

    // Retrieves a group by its ID, throws an exception if not found
    public Group getGroupByGroupId(Integer groupId) {
        return groupRepository.findById(groupId).orElseThrow(() ->
                new IllegalArgumentException("not found such group"));
    }

    // Updates the interests of a group
    public Group updateInterests(Integer groupId, String interests) {
        Group group = getGroupByGroupId(groupId);
        group.setInterests(interests);
        return groupRepository.save(group);
    }

    // Retrieves all groups
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    // Retrieves members of a group by its ID
    public List<GroupMember> getGroupMembersByGroupId(Integer groupId) {
        return groupMemberRepository.findByIdGroupId(groupId);
    }

    // Checks if a user is a member of a group
    public boolean isGroupMemberForCurrGroup(Integer groupId, Integer userId) {
        GroupMemberId id = new GroupMemberId(groupId, userId);
        return groupMemberRepository.findById(id).isPresent();
    }

    // Allows a user to join a public group
    public void joinPublicGroup(Integer groupId, Integer userId) {
        Group currGroup = getGroupByGroupId(groupId);
        User currUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found such user"));

        if (currGroup.getIsPrivate()) {
            throw new IllegalArgumentException("the group is private");
        }

        if (isGroupMemberForCurrGroup(groupId, userId)) {
            throw new IllegalArgumentException("you have already in this group");
        }

        GroupMember newMember = new GroupMember();
        GroupMemberId id = new GroupMemberId(groupId, userId);
        newMember.setId(id);
        newMember.setGroup(currGroup);
        newMember.setRole("member");
        newMember.setUser(currUser);

        groupMemberRepository.save(newMember);
    }

    // Removes a user from a group
    public void removeMemberFromCurrGroup(Integer groupId, Integer userId) {
        GroupMemberId currGroupMemberId = new GroupMemberId(groupId, userId);
        groupMemberRepository.deleteById(currGroupMemberId);
    }

    // Updates the privacy setting of a group
    public Group updateGroupType(Integer groupId, boolean isPrivate) {
        Group currGroup = getGroupByGroupId(groupId);
        currGroup.setIsPrivate(isPrivate);
        return groupRepository.save(currGroup);
    }

    // Retrieves all groups that the user has joined or created
    public List<Group> getMyGroups(Integer userId) {
        List<Group> myGroups = new ArrayList<>();
        List<GroupMember> joinedOrCreatedMembers = groupMemberRepository.findByIdUserId(userId);

        for (GroupMember member : joinedOrCreatedMembers) {
            myGroups.add(member.getGroup());
        }

        return myGroups;
    }

}
