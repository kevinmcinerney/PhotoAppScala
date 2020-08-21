package com.appsdeveloperblog.photoapp.api.users.service;

import com.appsdeveloperblog.photoapp.api.users.data.AlbumsServiceClient;
import com.appsdeveloperblog.photoapp.api.users.data.UserEntity;
import com.appsdeveloperblog.photoapp.api.users.data.UsersRepository;
import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;
import com.appsdeveloperblog.photoapp.api.users.ui.model.AlbumResponseModel;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UsersService {

    UsersRepository usersRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    //RestTemplate restTemplate;
    AlbumsServiceClient albumsServiceClient;
    Environment env;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AlbumsServiceClient albumsServiceClient, Environment env){
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        //this.restTemplate = restTemplate;
        this.albumsServiceClient = albumsServiceClient;
        this.env = env;

    }

    @Override
    public UserDto createUser(UserDto userDetails) {

        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class); // map json request body into java class UserEntity
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        usersRepository.save(userEntity);

        UserDto returnValue = modelMapper.map(userEntity, UserDto.class);

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = usersRepository.findByEmail(username);
        if(userEntity == null) throw new UsernameNotFoundException(username);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true,true,true,true,new ArrayList());
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = usersRepository.findByEmail(email);
        if(userEntity == null) throw new UsernameNotFoundException(email);
        return new ModelMapper().map(userEntity, UserDto.class);
    }

    /*
    Example of calling another webservice using
    a) restTemplate
    b) netflix feign
     */
    @Override
    public UserDto getUserByUserId(String userId) {

        UserEntity userEntity = usersRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException("User not found");

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        String albumsUrl = String.format(env.getProperty("albums.url"), userId);

//        ResponseEntity<List<AlbumResponseModel>> albumListResponse =
//                restTemplate.exchange(albumsUrl,
//                        HttpMethod.GET,
//                        null,
//                        new ParameterizedTypeReference<List<AlbumResponseModel>>() {});
//        List<AlbumResponseModel> albumList = albumListResponse.getBody();

        logger.info("Before calling albums microservice");
        List<AlbumResponseModel> albumList = albumsServiceClient.getAlbums(userId);
        logger.info("After calling albums microservice");

        //Exception Handling without Feign
//        List<AlbumResponseModel> albumList = null;
//        try {
//            albumList = albumsServiceClient.getAlbums(userId);
//        } catch (FeignException e) {
//            logger.error(e.getLocalizedMessage());
//        }


        userDto.setAlbums(albumList);

        return userDto;
    }
}
