package core.service;

import core.domain.models.Manifestation;
import core.requests.images.Base64ImageForManifestation;

public interface IImageService {
	Manifestation updateImageForManifestation(Base64ImageForManifestation base64ImageForManifestaion);
}
