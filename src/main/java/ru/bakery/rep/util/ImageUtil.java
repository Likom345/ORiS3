package ru.bakery.rep.util;

public class ImageUtil {

    // Определяем формат по содержимому файла, а не только по имени.
    public static String getContentType(byte[] image) {
        if (image == null || image.length < 8) {
            return null;
        }

        // PNG
        if (image.length >= 8 && image[0] == (byte) 0x89 && image[1] == 0x50
                && image[2] == 0x4E && image[3] == 0x47) {
            return "image/png";
        }

        // JPEG
        if (image[0] == (byte) 0xFF && image[1] == (byte) 0xD8
                && image[2] == (byte) 0xFF) {
            return "image/jpeg";
        }

        // WEBP: RIFF....WEBP
        if (image.length >= 12 && image[0] == 'R' && image[1] == 'I' && image[2] == 'F' && image[3] == 'F'
                && image[8] == 'W' && image[9] == 'E' && image[10] == 'B' && image[11] == 'P') {
            return "image/webp";
        }

        return null;
    }
}
