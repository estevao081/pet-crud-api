package dev.estv.pet_crud_api.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
public class ReturnImageURL {

    private final Cloudinary cloudinary;

    public ReturnImageURL(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String imageUrl(MultipartFile image) {
        try {
            Map uploadResult = cloudinary.uploader()
                    .upload(image.getBytes(), ObjectUtils.emptyMap());

            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao enviar imagem");
        }
    }
}
