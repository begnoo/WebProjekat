package repository.utils.loaders.single;

import core.domain.models.Buyer;
import core.domain.models.Comment;
import core.domain.models.Manifestation;
import core.domain.models.User;
import core.repository.IDependencyLoader;
import repository.DbContext;
import repository.DbSet;

public class CommentDependencyLoader implements IDependencyLoader<Comment> {
	private DbSet<User> users;
	private DbSet<Manifestation> manifestations;
	
	@SuppressWarnings("unchecked")
	public CommentDependencyLoader(DbContext context)
	{
		this.users = (DbSet<User>) context.getSet(User.class);
		this.manifestations = (DbSet<Manifestation>) context.getSet(Manifestation.class);
	}
	
	@Override
	public void load(Comment comment) {
		Buyer commenter = (Buyer) users.read(comment.getBuyerId());
		comment.setBuyer(commenter);
		
		Manifestation manifestation = manifestations.read(comment.getManifestationId());
		comment.setManifestation(manifestation);
	}
}