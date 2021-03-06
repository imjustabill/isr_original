package com.astrodoorways.converter.db.imagery;

import com.astrodoorways.converter.db.filesystem.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("metadataDAO")
public interface MetadataDAO extends JpaRepository<Metadata, Long> {

	List<Metadata> findByFileInfoJob(Job job);

	int countByFileInfoJob(Job job);

}