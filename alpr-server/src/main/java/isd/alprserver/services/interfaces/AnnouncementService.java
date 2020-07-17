package isd.alprserver.services.interfaces;

import isd.alprserver.dtos.CommentDTO;
import isd.alprserver.model.Announcement;
import isd.alprserver.model.Comment;
import isd.alprserver.model.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface AnnouncementService {
    void add(Announcement announcement);
    void remove(long id);
    void update(Announcement announcement);
    List<Announcement> getAll();
    Optional<Announcement> getById(long id);
    void addComment(long id, Comment comment);
    List<CommentDTO> getAllComments(long id) throws UserNotFoundException;
}
