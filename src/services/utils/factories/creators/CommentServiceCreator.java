package services.utils.factories.creators;

import core.domain.models.Comment;
import core.domain.models.Manifestation;
import core.domain.models.User;
import core.repository.IRepository;
import core.service.ICommentService;
import core.service.IServiceCreator;
import repository.CommentRepository;
import repository.DbContext;
import repository.ManifestationRepository;
import repository.UserRepository;
import services.CommentService;

public class CommentServiceCreator implements IServiceCreator<ICommentService> {

	@Override
	public ICommentService create(DbContext context) {
		IRepository<Comment> commentRepository = new CommentRepository(context);
		IRepository<Manifestation> manifestationRepository = new ManifestationRepository(context);
		IRepository<User> userRepository = new UserRepository(context);
		
		return new CommentService(commentRepository, manifestationRepository, userRepository);
	}

}
