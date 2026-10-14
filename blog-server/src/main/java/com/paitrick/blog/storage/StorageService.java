package com.paitrick.blog.storage;
public interface StorageService{
  String upload(String filename,byte[] content);
  void delete(String url);
}

