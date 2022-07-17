package com.app.controller;



import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.managementuser.DiscoDTO;
import com.app.dto.managementuser.SentimentDTO;
import com.app.persistence.model.es.managementuser.Disco;
import com.app.service.DiscoService;
import com.app.util.ApiConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping(ApiConstants.BASE_CLIENT_URI)
@Api(value = "Disco", tags = "disco")
public class DiscoController {

	@Autowired
	private DiscoService discoServices;
	@Autowired
	private Mapper mapper;
    private static final Logger LOG = LoggerFactory.getLogger(DiscoController.class);

	@ApiOperation(value = "Get Disco", tags = "Disco")
	@RequestMapping(value = "{idDisco}", method = RequestMethod.GET)
	public DiscoDTO getDisco(@PathVariable String idDisco) {
        LOG.info(String.format("Disco consulta - disco/%s/consulta", idDisco));
		Disco disco = discoServices.getDisco(idDisco);
		return mapper.map(disco, DiscoDTO.class);
	}
	
	@ApiOperation(value = "Get Sentiment", tags = "Disco")
	@RequestMapping(method = RequestMethod.GET)
	public SentimentDTO getSentiment() {
		SentimentDTO sentimentDTO = new SentimentDTO();
        LOG.info(String.format("Disco get sentimient"));
		Disco disco = discoServices.getSentiment();
		sentimentDTO.setGenre(disco.getGenre()); 
		return sentimentDTO;
	}

	@ApiOperation(value = "Get Words", tags = "Disco")
	@RequestMapping(value = "word/{word}", method = RequestMethod.GET)
	public int getWord(@PathVariable String word) {
        LOG.info(String.format("Disco consulta - disco/%s/words", word));
		return discoServices.getWords(word);
	}
	
	@ApiOperation(value = "Get allDisco", tags = "Disco")
	@RequestMapping(value = "allDisco",method = RequestMethod.GET)
	public List<Disco> getAllDisco() {
		LOG.info(String.format("Disco get all disco"));
		List<Disco> listDisco = discoServices.getAllDisco();
		return listDisco;
	}
	
	@ApiOperation(value = "Get dataDisc", tags = "Disco")
	@RequestMapping(value = "dataDisco",method = RequestMethod.GET)
	public void getDataDisc() {
		LOG.info(String.format("Disco get data disco"));
		discoServices.getDataMp3();
	}
	
        
}
