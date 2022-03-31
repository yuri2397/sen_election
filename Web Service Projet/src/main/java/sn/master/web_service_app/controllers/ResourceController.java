package sn.master.web_service_app.controllers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sn.master.web_service_app.entities.Media;
import sn.master.web_service_app.repository.MediaRepository;
import sn.master.web_service_app.services.FileStorageService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping(path = "/api/resource")
@NoArgsConstructor
public class ResourceController {
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    private MediaRepository mediaRepository;
    private FileStorageService fileStorageService;

    @Autowired
    public ResourceController(FileStorageService fileStorageService, MediaRepository mediaRepository){
        this.fileStorageService = fileStorageService;
        this.mediaRepository = mediaRepository;
    }

    @PostMapping("/uploadFile")
    public Media uploadFile(@RequestParam("cni") String cni, @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(cni,file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return this.mediaRepository.save(new Media(fileName, fileDownloadUri,
                file.getContentType(), file.getSize()));
    }

    @PostMapping("/uploadMultipleFiles")
    public List<Media> uploadMultipleFiles(@RequestParam("cni") String cni,@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(cni,file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
            Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
