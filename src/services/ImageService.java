package services;

import java.io.FileOutputStream;
import java.util.Base64;

import core.domain.models.Manifestation;
import core.repository.IRepository;
import core.requests.images.Base64ImageForManifestation;
import core.service.IImageService;

public class ImageService implements IImageService {

	private IRepository<Manifestation> manifestationRepository;

	public ImageService(IRepository<Manifestation> manifestationRepository) {
		this.manifestationRepository = manifestationRepository;
	}

	@Override
	public Manifestation updateImageForManifestation(Base64ImageForManifestation base64ImageForManifestaion) {
		
		byte[] imageByteArray = getImageByteArray(base64ImageForManifestaion.getBase64Representation());
		String imagePath = getImagePath(base64ImageForManifestaion);
		try (FileOutputStream fileOutputStream = new FileOutputStream(imagePath)) {
			fileOutputStream.write(imageByteArray);
		} catch (Exception e) {
			System.out.println(imagePath);
			System.out.println("Error while trying to save image to file system.");
			return null;
		}
		
		Manifestation manifestationForUpdate = manifestationRepository.read(base64ImageForManifestaion.getManifestationId());
		String requestRootPath = "../WebProjekat/rest/";
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
