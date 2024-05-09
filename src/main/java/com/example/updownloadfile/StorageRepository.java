package com.example.updownloadfile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<FileData, Long> {
    FileData findByName(String name);
}
