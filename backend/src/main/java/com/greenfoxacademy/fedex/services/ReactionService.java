package com.greenfoxacademy.fedex.services;

import com.greenfoxacademy.fedex.exceptions.InvalidMemeException;
import com.greenfoxacademy.fedex.exceptions.InvalidReactionException;
import com.greenfoxacademy.fedex.models.*;
import com.greenfoxacademy.fedex.models.reactions.*;
import com.greenfoxacademy.fedex.repositories.CommentRepository;
import com.greenfoxacademy.fedex.repositories.ReactionGiversRepository;
import com.greenfoxacademy.fedex.repositories.ReactionGiversValueRepository;
import com.greenfoxacademy.fedex.repositories.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReactionService {
    private ReactionRepository reactionRepository;
    private ReactionGiversRepository reactionGiversRepository;
    private ReactionGiversValueRepository reactionGiversValueRepository;
    private CommentRepository commentRepository;

    @Autowired
    public ReactionService(ReactionRepository reactionRepository,
                           ReactionGiversRepository reactionGiversRepository,
                           ReactionGiversValueRepository reactionGiversValueRepository,
                           CommentRepository commentRepository) {
        this.reactionRepository = reactionRepository;
        this.reactionGiversRepository = reactionGiversRepository;
        this.reactionGiversValueRepository = reactionGiversValueRepository;
        this.commentRepository = commentRepository;
    }

    public void addUserReactionAndComment(User user, Meme meme, UserMemeDTO reactionRequest)
            throws InvalidReactionException {
        checkIfValidReactionRequest(reactionRequest);
        saveAllReactions(user, meme, reactionRequest.getReactionList());
        saveComment(reactionRequest.getComment(), meme, user);
    }

    public UserMemeDTO getMetadataByMemeAndUser(User user, Meme meme) throws InvalidMemeException {
        List<ReactionDTO> reactionList = getReactionsByUserAndMeme(user, meme);
        String comment = getCommentByUserAndMeme(user, meme);
        return new UserMemeDTO(reactionList, comment);
    }

    private String getCommentByUserAndMeme(User user, Meme meme) {
        Optional<Comment> optionalComment = commentRepository.findByUserAndMeme(user, meme);
        if (optionalComment.isPresent()) {
            return optionalComment.get().getCommentText();
        }
        return "";
    }

    private List<ReactionDTO> getReactionsByUserAndMeme(User user, Meme meme) {
        List<ReactionGiversValue> existingReactionsByUserAndMeme =
                getExistingReactionsByUserAndMeme(user, meme);
        List<ReactionType> existingReactionTypes = getExistingReactionTypes(existingReactionsByUserAndMeme);
        List<ReactionDTO> reactionList = new ArrayList<>();
        addReactionDTOsToList(existingReactionsByUserAndMeme, existingReactionTypes, reactionList);
        return reactionList;
    }

    private void addReactionDTOsToList(List<ReactionGiversValue> existingReactionsByUserAndMeme,
                                       List<ReactionType> existingReactionTypes,
                                       List<ReactionDTO> reactionList) {
        getReactionListOriginalOrder()
                .forEach(reaction -> {
                    if (existingReactionTypes.contains(reaction.getId())) {
                        reactionList.add(getReactionWithTypeFromRGVList(existingReactionsByUserAndMeme, reaction.getId()));
                    } else {
                        reactionList.add(new ReactionDTO(reaction.getId(), 0));
                    }
                });
    }

    private List<ReactionType> getExistingReactionTypes(List<ReactionGiversValue> existingReactionsByUserAndMeme) {
        return existingReactionsByUserAndMeme.stream()
                .map(rgv -> rgv.getReactionGivers().getReaction().getId())
                .collect(Collectors.toList());
    }

    private List<ReactionGiversValue> getExistingReactionsByUserAndMeme(User user, Meme meme) {
        return reactionGiversValueRepository.findByUser(user).stream()
                .filter(rgv -> rgv.getReactionGivers().getMeme().getId().equals(meme.getId()))
                .collect(Collectors.toList());
    }

    private ReactionDTO getReactionWithTypeFromRGVList(List<ReactionGiversValue> RGVList, ReactionType reactionType) {
        Optional<ReactionGiversValue> optionalRGV = RGVList.stream()
                .filter(rgv -> rgv.getReactionGivers().getReaction().getId().equals(reactionType))
                .findFirst();
        return new ReactionDTO(reactionType, optionalRGV.get().getAmount());
    }

    private void saveComment(String commentText, Meme meme, User user) {
        if (commentText != null) {
            Optional<Comment> optionalComment = ((List<Comment>) commentRepository.findAll()).stream()
                    .filter(comment1 -> (comment1.getId().getMemeId().equals(meme.getId())
                            && comment1.getId().getUserId().equals(user.getId())))
                    .findFirst();
            if (optionalComment.isPresent()) {
                updateComment(optionalComment.get(), commentText);
            } else {
                createNewComment(meme, user, commentText);
            }
        }
    }

    private void createNewComment(Meme meme, User user, String commentText) {
        Comment comment = commentRepository.save(new Comment(meme, user, commentText));
        meme.addComment(comment);
        user.addComment(comment);
    }

    private void updateComment(Comment comment, String commentText) {
        comment.setCommentText(commentText);
        commentRepository.save(comment);
        comment.getUser().addComment(comment);
        comment.getMeme().addComment(comment);
    }

    private List<Reaction> getReactionListOriginalOrder() {
        return reactionRepository.findAllOriginalOrder();
    }

    private void saveAllReactions(User user, Meme meme, List<ReactionDTO> reactionList) {
        HashMap<ReactionType, Integer> reactionListMap = reactionListToHashMap(reactionList);
        getReactionListOriginalOrder()
                .forEach(reaction ->
                        saveReaction(user, meme, reaction, reactionListMap.getOrDefault(reaction.getId(), 0)));
    }

    private void saveReaction(User user, Meme meme, Reaction reaction, Integer amount) {
        Optional<ReactionGivers> optionalReactionGivers =
                reactionGiversRepository.findById_MemeIdAndId_ReactionId(meme.getId(), reaction.getId());
        if (!optionalReactionGivers.isPresent()) {
            createNewReactionGivers(user, meme, reaction, amount);
        } else {
            updateReactionGivers(optionalReactionGivers.get(), user, amount);
        }
    }

    private HashMap<ReactionType, Integer> reactionListToHashMap(List<ReactionDTO> reactionList) {
        HashMap<ReactionType, Integer> reactionMap = new HashMap<ReactionType, Integer>();
        reactionList.forEach(reactionDTO -> reactionMap.put(reactionDTO.getReactionType(), reactionDTO.getAmount()));
        return reactionMap;
    }

    private void createNewReactionGivers(User user, Meme meme, Reaction reaction, Integer amount) {
        ReactionGivers reactionGivers = reactionGiversRepository.save(new ReactionGivers(meme, reaction));
        ReactionGiversValue reactionGiversValue =
                reactionGiversValueRepository.save(new ReactionGiversValue(reactionGivers, user, amount));
        reactionGivers.addToReactionGiversValueList(reactionGiversValue);
    }

    private void updateReactionGivers(ReactionGivers reactionGivers, User user, Integer amount) {
        Optional<ReactionGiversValue> optionalReactionGiversValue =
                reactionGivers.getReactionGiversValueList().stream()
                        .filter(rgv -> rgv.getUser().getId().equals(user.getId()))
                        .findFirst();
        if (optionalReactionGiversValue.isPresent()) {
            updateReactionGiversValue(amount, optionalReactionGiversValue.get(), reactionGivers);
        } else {
            createNewReactionGiversValue(reactionGivers, user, amount);
        }
    }

    private void createNewReactionGiversValue(ReactionGivers reactionGivers, User user, Integer amount) {
        ReactionGiversValue reactionGiversValue =
                reactionGiversValueRepository.save(new ReactionGiversValue(reactionGivers, user, amount));
        reactionGivers.addToReactionGiversValueList(reactionGiversValue);
    }

    private void updateReactionGiversValue(Integer amount, ReactionGiversValue reactionGiversValue, ReactionGivers reactionGivers) {
        reactionGiversValue.setAmount(amount);
        reactionGiversValue = reactionGiversValueRepository.save(reactionGiversValue);
        reactionGivers.addToReactionGiversValueList(reactionGiversValue);
    }

    private void checkIfValidReactionRequest(UserMemeDTO reactionRequest) throws InvalidReactionException {
        if (reactionRequest == null || reactionRequest.getReactionList() == null) {
            throw new InvalidReactionException("Missing parameter: reactions");
        }
        for (ReactionDTO reactionDTO : reactionRequest.getReactionList()) {
            checkIfValidReaction(reactionDTO);
        }
    }

    private void checkIfValidReaction(ReactionDTO reactionDTO) throws InvalidReactionException {
        if (reactionDTO.getReactionType() == null) {
            throw new InvalidReactionException("Missing parameter: reaction type");
        }
        checkIfValidReactionValue(reactionDTO);
        reactionRepository.findById(
                reactionDTO.getReactionType()).orElseThrow(() -> new InvalidReactionException("Invalid Reaction Type"));
    }

    private void checkIfValidReactionValue(ReactionDTO reactionDTO) throws InvalidReactionException {
        if (reactionDTO.getAmount() == null) {
            throw new InvalidReactionException("Missing parameter: amount");
        }
        if (!(reactionDTO.getAmount() >= 0 && reactionDTO.getAmount() <= 10)) {
            throw new InvalidReactionException("Reaction amount should be between 0 and 10");
        }
    }

    public List<ReactionDTO> reactionsOfOneMeme(Meme meme) {
        List<ReactionDTO> reactionList = new ArrayList<>();
        meme.getReactionGiversList()
                .forEach(rg -> reactionList.add(new ReactionDTO(rg.getReaction().getId(), getValueOfReaction(rg))));
        return reactionList;
    }

    private Integer getValueOfReaction(ReactionGivers reactionGivers) {
        return reactionGivers.getReactionGiversValueList()
                .stream().mapToInt(ReactionGiversValue::getAmount)
                .sum();
    }

    public List<ReactionGivers> addAllEmptyReactionToMeme(Meme meme) {
        List<ReactionGivers> reactionGiversList = new ArrayList<>();
        reactionRepository.findAll()
                .forEach(reaction -> reactionGiversList.add(reactionGiversRepository.save(new ReactionGivers(meme, reaction))));
        return reactionGiversList;
    }
}
