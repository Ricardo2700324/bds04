package com.devsuperior.bds04.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.EventRepository;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;
	

	
	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {
			Event entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new EventDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not Found " + id);
		}
	}

	private void copyDtoToEntity(EventDTO dto, Event entity) {
		entity.setName(dto.getName());
		entity.setDate(dto.getDate());
		entity.setUrl(dto.getUrl());
		
//		entity.getCity().clear();
//		for(CityDTO citDto : dto.getCity()) {
//		  	City cit = repository.getOne(citDto.getId());
//		  	entity.getCity().add(cit);
		}

	public List<EventDTO> findAll() {
		List<Event> list = repository.findAll(Sort.by("name"));
		return list.stream().map(x -> new EventDTO(x)).collect(Collectors.toList());
	}

	@Transactional
	public EventDTO insert(EventDTO dto) {
		Event entity = new Event();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new EventDTO(entity);
	}
		
	}



