package com.example.updownloadfile;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "FileData")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] fileData;
}
