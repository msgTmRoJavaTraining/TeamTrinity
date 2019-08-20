package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment implements Serializable {
    private byte[] attachmentBytes;
    private String fileName;
    private String fileExtension;
    private String fileMimeType;
}
