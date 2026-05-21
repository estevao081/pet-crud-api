package dev.estv.pet_crud_api.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import dev.estv.pet_crud_api.exception.exceptions.InvalidImageException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@Component
public class ReturnImageURL {

    private final Cloudinary cloudinary;

    public ReturnImageURL(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String imageUrl(MultipartFile image) {

        final long MAX_FILE_SIZE = 5 * 1024 * 1024;

        if (image.isEmpty() || image.getSize() > MAX_FILE_SIZE) {
            throw new InvalidImageException();
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(image.getInputStream());

            if (bufferedImage.getWidth() > 4000 || bufferedImage.getHeight() > 4000) {
                throw new InvalidImageException();
            }

            Map params = ObjectUtils.asMap(
                    "folder", "pets",
                    "transformation", new Transformation()
                            .width(800)
                            .height(800)
                            .crop("limit")
                            .quality("auto")
                            .fetchFormat("auto")
            );

            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), params);

            return uploadResult.get("secure_url").toString();

        } catch (IOException e) {
            throw new RuntimeException("Error on send image");
        }
    }
}
