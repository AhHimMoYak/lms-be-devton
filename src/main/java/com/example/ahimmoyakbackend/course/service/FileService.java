package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.course.common.ContentType;
import com.example.ahimmoyakbackend.course.dto.FileInfoDto;
import com.example.ahimmoyakbackend.course.entity.ContentsMaterial;
import com.example.ahimmoyakbackend.course.repository.ContentsMaterialRepository;
import com.example.ahimmoyakbackend.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final ContentsMaterialRepository contentsMaterialRepository;

    private static final long MAX_FILE_SIZE = 1024L * 1024L * 1024L * 10L; // 10GB


    public FileInfoDto saveFile(MultipartFile file, ContentType contentType) {
        if (file.getSize() > MAX_FILE_SIZE) throw new ApiException(HttpStatus.PAYLOAD_TOO_LARGE, "파일용량이 너무 큼");
        String postfix = getPostfix(file.getOriginalFilename());
        String savedName = String.valueOf(UUID.randomUUID());
        Path filePath;
        if (contentType.equals(ContentType.VIDEO)) {
            filePath = Paths.get(getPath("/video"), savedName + postfix);
        } else {
            filePath = Paths.get(getPath("/material"), savedName + postfix);
        }
        try (OutputStream os = Files.newOutputStream(filePath)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        if (contentType.equals(ContentType.VIDEO)) {
            double duration = getVideoDuration(filePath.toFile());
            return FileInfoDto.builder()
                    .path(Paths.get("storage", "video").toString())
                    .originName(subPostfix(file.getOriginalFilename()))
                    .savedName(savedName)
                    .postfix(postfix)
                    .duration(duration)
                    .build();
        }
        return FileInfoDto.builder()
                .path(Paths.get("storage", "material").toString())
                .originName(subPostfix(file.getOriginalFilename()))
                .savedName(savedName)
                .postfix(postfix)
                .build();
    }

    public ResponseEntity<ResourceRegion> streamVideo(HttpHeaders httpHeaders, String fileInfo) {
        try{
            Path path = Paths.get("storage", "video", fileInfo);
            Resource resource = new FileSystemResource(path);
            long chunkSize = 1024*1024;
            long contentLength = resource.contentLength();
            ResourceRegion resourceRegion;
            try{
                HttpRange httpRange;
                if(httpHeaders.getRange().stream().findFirst().isPresent()){
                    httpRange = httpHeaders.getRange().stream().findFirst().get();
                    long start = httpRange.getRangeStart(contentLength);
                    long end = httpRange.getRangeEnd(contentLength);
                    long rangeLength = Long.min(chunkSize, end-start+1);

                    resourceRegion = new ResourceRegion(resource, start, rangeLength);
                }
                else{
                    resourceRegion = new ResourceRegion(resource, 0, Long.min(chunkSize, resource.contentLength()));
                }
            }
            catch(Exception e){
                long rangeLength = Long.min(chunkSize, resource.contentLength());
                resourceRegion = new ResourceRegion(resource, 0, rangeLength);
            }
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES)) // 10분
                    .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .body(resourceRegion);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Resource> downloadMaterial(String fileInfo) {
        Path path = Paths.get("storage", "material", fileInfo);
        ContentsMaterial material = contentsMaterialRepository.findBySavedName(subPostfix(fileInfo));
        String encodedFilename = URLEncoder.encode(material.getOriginName()+material.getPostfix(), StandardCharsets.UTF_8);
        try{
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentDisposition(ContentDisposition.builder("inline").filename(encodedFilename).build());
            httpHeaders.setContentType(MediaType.APPLICATION_PDF);
            Resource resource = new InputStreamResource(Files.newInputStream(path)); // save file resource
            return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }



    private String getPostfix(String fileName) {
        if (fileName == null || !fileName.contains(".")) return "";
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private String subPostfix(String fileName) {
        if (fileName == null || !fileName.contains(".")) return fileName;
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    private String getPath(String optionalPath) {
        String directory = "storage" + optionalPath;
        Path path = Paths.get(directory);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "저장공간을 찾을 수 없음");
            }
        }
        return path.toString();
    }

    private double getVideoDuration(File videoFile) {
        log.info("Video file path: {}", videoFile.getAbsolutePath());
        log.info("File exists: {}", videoFile.exists());
        try {
            // FrameGrab을 사용해 동영상 메타데이터에서 총 프레임 수와 FPS를 얻음
            FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(videoFile));
            grab.seekToSecondPrecise(0);

            int totalFrames = 0;
            Picture picture;

            while ((picture = grab.getNativeFrame()) != null) {
                totalFrames++;
            }

            // FPS 가져오기
            double fps = grab.getVideoTrack().getMeta().getTotalDuration() / totalFrames;

            return totalFrames / fps;
        } catch (IOException | JCodecException e) {
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }
}
