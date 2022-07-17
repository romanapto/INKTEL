package com.app.repository.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.app.persistence.model.es.managementuser.Disco;
import com.app.repository.es.custom.DiscoRepositoryCustom;

@Repository
public interface DiscoRepository extends ElasticsearchRepository<Disco, String>, DiscoRepositoryCustom {


}
