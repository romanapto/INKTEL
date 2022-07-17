package com.dto.admin;

import com.app.dto.managementuser.DiscoDTO;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class DiscoDtoTest {

    private static ValidatorFactory factory;

    private static Validator validator;

    @BeforeClass
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateRoleName() {
        DiscoDTO roleDTO = getDiscoDTO();
        Assert.assertEquals(0, validator.validate(roleDTO).size());
    }

    @Test
    public void validateWithInvalidName() {
    	DiscoDTO discoDTO = getDiscoDTO();
    	discoDTO.setId("");

        Set<ConstraintViolation<DiscoDTO>> violations = validator.validate(discoDTO);
        Assert.assertEquals(1, violations.size());
        for (ConstraintViolation<DiscoDTO> violation : violations) {
            Assert.assertEquals("", violation.getInvalidValue());
        }
    }

    private DiscoDTO getDiscoDTO() {
    	DiscoDTO discoDto = new DiscoDTO();
        discoDto.setId("1256");
    	discoDto.setAlbum("best1");
        discoDto.setArtist("elly jr");
        return discoDto;
    }
}
