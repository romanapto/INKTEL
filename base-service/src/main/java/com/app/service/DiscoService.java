package com.app.service;

import com.app.persistence.model.es.managementuser.Disco;
import com.app.repository.es.DiscoRepository;
import com.google.type.Date;

import de.sciss.audiofile.AudioFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.stream.Stream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.spi.AudioFileReader;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DiscoService {


	@Autowired
    private DiscoRepository discoRepository;
	
    public Disco getSentiment() {
        Disco disco = new Disco();
       	disco = mp3Analice(true);
        return disco;
    }
    
    public Disco mp3Analice(boolean save) {
    	String fileLocation = "/home/julian/Downloads/20_Enfermedades.mp3";
    	Disco disco = new Disco();
        try {

        InputStream input = new FileInputStream(new File(fileLocation));
        ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata();
        Parser parser = new Mp3Parser();
        ParseContext parseCtx = new ParseContext();
        parser.parse(input, handler, metadata, parseCtx);
        input.close();

        String[] metadataNames = metadata.names();

        for(String name : metadataNames){
        System.out.println(name + ": " + metadata.get(name));
        }

        System.out.println("----------------------------------------------");
        System.out.println("Title: " + metadata.get("title"));
        System.out.println("Artists: " + metadata.get("xmpDM:artist"));
        System.out.println("Composer : "+metadata.get("xmpDM:composer"));
        System.out.println("Genre : "+metadata.get("xmpDM:genre"));
        System.out.println("Album : "+metadata.get("xmpDM:album"));
        disco.setAlbum(metadata.get("xmpDM:album"));
        disco.setArtist(metadata.get("xmpDM:artist"));
        disco.setComposer(metadata.get("xmpDM:composer"));
        disco.setGenre(metadata.get("xmpDM:genre"));
        disco.setTitle(metadata.get("title"));
        if (save) {
        	saveDisco(disco);
        }
        } catch (FileNotFoundException e) {
        e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        } catch (SAXException e) {
        e.printStackTrace();
        } catch (TikaException e) {
        e.printStackTrace();
        }
        return disco;
    }
    	
    
    public Disco getDisco(String idDisco) {
    	return findDisco(idDisco);
    }
    
    public List<Disco> getAllDisco() {
    	return findAllDisco();
    }
    
    public int getWords(String word) {
    	Disco disco = new Disco();
    	int count=0;
       	disco = mp3Analice(false);
        String myStr = disco.getTitle();
        String[] words = myStr.split("\\s+");
        for(String name : words){
        	if (word.equals(name)) {
        		count++;
        	}
            System.out.println(name);
         }
        System.out.println("Word Count is: "+words.length);
        return count;
    }
    
    public void getDataMp3() {
    	try {
    		org.jaudiotagger.audio.AudioFile audioFileio = AudioFileIO.read(new File("/home/julian/Downloads/20_Enfermedades.mp3"));
    		processFile("/home/julian/Downloads/20_Enfermedades.mp3"); 
    		 //duration = audioFile.getAudioHeader().getTrackLength();
		} catch (IOException | CannotReadException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void processFile(String fileLocation) throws IOException {
        InputStream inputStream = new FileInputStream(fileLocation);
        Scanner sc = null;

        //text file: 25.1_-_Marvel_Graph.txt, size 1.5MB
        //System.out.println("Data reading started = " + new Date());
        if (inputStream != null) {
            StringBuilder txtData = new StringBuilder("");
            try {
                sc = new Scanner(inputStream, "UTF-8");
                Stream<MatchResult> prueba = sc.findAll("what");
                while (sc.hasNextLine()) {
                    txtData.append(sc.nextLine());
                }
                // note that Scanner suppresses exceptions
                if (sc.ioException() != null) {
                    throw sc.ioException();
                }
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (sc != null) {
                    sc.close();
                }
            }
            txtData.toString();
        }
        //System.out.println("Data reading finished = " + new Date());

    }
    private Disco saveDisco(Disco disco) {
        return discoRepository.save(disco);
    }
    
    private Disco findDisco(String idDisco) {
        return discoRepository.findById(idDisco).get();
    }
   
    private List<Disco> findAllDisco() {
    	List<Disco> listdisco = new ArrayList<Disco>();
    	Iterable<Disco> listIterableDisco = discoRepository.findAll();
    	listIterableDisco.forEach(listdisco::add);
        return listdisco;
    }
    

}
