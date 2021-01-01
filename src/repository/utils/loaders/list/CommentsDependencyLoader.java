package repository.utils.loaders.list;

import java.util.List;

import core.domain.models.Comment;
import core.repository.IDependencyLoader;
import repository.DbContext;
import repository.utils.loaders.single.CommentDependencyLoader;

public class CommentsDependencyLoader implements IDependencyLoader<List<Comment>> {
	private CommentDependencyLoader commentDependencyLoader;
	
	public CommentsDependencyLoader(DbContext context)
	{
		commentDependencyLoader = new CommentDependencyLoader(context);
	}
	
	@Override
	public void load(List<Comment> comments)  {
		for(Comment comment : comments)
		{
			commentDependencyLoader.load(comment);
		}
	}
}