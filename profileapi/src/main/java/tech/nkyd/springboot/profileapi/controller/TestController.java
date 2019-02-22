package tech.nkyd.springboot.profileapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

	@RequestMapping("/")
	public String home() {
		StringBuffer message = new StringBuffer();
		message.append("Hey Naresh, spring boot application is up and running");
		message.append("\n");
		message.append("api supported as of now /profiles");
		return message.toString();
	}
	@Autowired
    private ProfileRepository profileRepository;
    @GetMapping("/profiles")
    public List<Profile> getAllProfiles() {
       return profileRepository.findAll();
    }

    @GetMapping("/profiles/{id}")
    public Profile getProfile(@PathVariable String id) {
        return profileRepository.findById(id);
    }

    @PostMapping("/profiles")
    public void createProfile(@RequestBody Profile profile) {
    	profileRepository.save(profile);
    }

    @DeleteMapping("/profiles/{id}")
    public Profile deleteProfile(@PathVariable String id) {
        return profileRepository.deleteById(id);
    }
   
}