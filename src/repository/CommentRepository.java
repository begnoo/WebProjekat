package repository;

import core.domain.models.Comment;
import core.repository.ILazyLoader;
import repository.utils.loaders.LazyLoader;

public class CommentRepository extends Repository<Comment> {
	public CommentRepository(DbContext context) {
		super(context, Comment.class);
	}
	
	@Override
	public Comment create(Comment comment) {
		Comment addedComment = super.create(comment);
		loadDependencies(addedComment);

		return addedComment;
	}
	
	
	@Override
	public Comment update(Comment commentForUpdate) {
		Comment updatedComment = super.update(commentForUpdate);
		loadDependencies(updatedComment);
		
		return updatedComment;
	}
	
	private void loadDependencies(Comment comment) {
		ILazyLoader loader = new LazyLoader(context);
		loader.loadDependencies(comment);
	}
}
