package services;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Base64;

import core.domain.models.Manifestation;
import core.exceptions.BadLogicException;
import core.exceptions.MissingEntityException;
import core.repository.IRepository;
import core.requests.images.Base64ImageForManifestation;
import core.service.IImageService;

public class ImageService implements IImageService {

	private static String requestRootPath = "../WebProjekat/rest/";
	private IRepository<Manifestation> manifestationRepository;

	public ImageService(IRepository<Manifestation> manifestationRepository) {
		this.manifestationRepository = manifestationRepository;
	}

	@Override
	public BufferedInputStream getImage(String fileName) {
		String filePath = "images/" + fileName;
		try {
			return new BufferedInputStream(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			throw new MissingEntityException(String.format("Error while trying to load image from %s.", filePath));
		} 
	}

	@Override
	public Manifestation updateImageForManifestation(Base64ImageForManifestation base64ImageForManifestaion) {
		Manifestation manifestationForUpdate = manifestationRepository.read(base64ImageForManifestaion.getManifestationId());
		if(manifestationForUpdate == null) {
			throw new MissingEntityException(String.format("Manifestation with id = %s does not exists.", base64ImageForManifestaion.getManifestationId()));
		}
		
		byte[] imageByteArray = getImageByteArray(base64ImageForManifestaion.getBase64Representation());
		String imagePath = getImagePath(base64ImageForManifestaion);
		try (FileOutputStream fileOutputStream = new FileOutputStream(imagePath)) {
			fileOutputStream.write(imageByteArray);
		} catch (Exception e) {
			System.out.println(imagePath);
			throw new BadLogicException("Error while trying to save image to file system");
		}
				
		manifestationForUpdate.setImagePath(requestRootPath + imagePath);
		manifestationRepository.update(manifestationForUpdate);
		
		return manifestationForUpdate;
	}
	
	private String getImagePath(Base64ImageForManifestation base64ImageForManifestaion) {
		return "images/" + base64ImageForManifestaion.getManifestationId().toString() + ".jpg";
	}

	private byte[] getImageByteArray(String base64string) {
		return Base64.getDecoder().decode(base64string);
	}
}
