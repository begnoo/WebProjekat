package core.service;

import java.util.List;
import java.util.UUID;

import core.domain.enums.CommentStatus;
import core.domain.models.Comment;

public interface ICommentService extends ICrudService<Comment> {
	List<Comment> readByManifestationId(UUID manifestationId);
	List<Comment> readNonPendingCommentsByManifestationId(UUID manifestationId);
	List<Comment> readByManifestationIdAndStatus(UUID manifestationId, CommentStatus commentStatus);
}
