package services;

import java.util.List;

import core.domain.dto.UsersSearchParamethers;
import core.domain.models.User;
import core.repository.IDbSetStream;
import core.repository.IRepository;
import core.service.IAdvanceSearchService;

public class UsersSearchService implements IAdvanceSearchService<User, UsersSearchParamethers> {

	private IRepository<User> repository;
	
	public UsersSearchService(IRepository<User> repository)
	{
		this.repository = repository;
	}
	
	@Override
	public List<User> search(UsersSearchParamethers searchParamethers) {
		IDbSetStream<User> stream = repository.getStream()
				.filter(user -> user.getName().toLowerCase().contains(searchParamethers.getName().toLowerCase()))
				.filter(user -> user.getSurname().toLowerCase().contains(searchParamethers.getSurname().toLowerCase()))
				.filter(user -> user.getUsername().toLowerCase().contains(searchParamethers.getUsername().toLowerCase()));

		return stream.collect();
	}

}
