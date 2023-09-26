package dev.aziz.jwt.backend.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface VideoStorageService {
  public void init();

  public void save(MultipartFile file);

  public Resource load(String filename);
  
  public boolean delete(String filename);

  public void deleteAll();

  public Stream<Path> loadAll();

  public Long getVideoIdByName(String name);
}
