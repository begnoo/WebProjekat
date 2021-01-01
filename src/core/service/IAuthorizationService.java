package core.service;

import core.domain.dto.AuthorizedUser;
import core.domain.dto.Credidentals;

public interface IAuthorizationService {
	AuthorizedUser authorize(Credidentals credidentals);
}
