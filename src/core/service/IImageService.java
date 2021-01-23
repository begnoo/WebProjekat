package core.service;

import java.io.BufferedInputStream;

import core.domain.models.Manifestation;
import core.requests.images.Base64ImageForManifestation;

public interface IImageService {
	BufferedInputStream getImage(String fileName);
	
	Manifestation updateImageForManifestation(Base64ImageForManifestation base64ImageForManifestaion);
}
