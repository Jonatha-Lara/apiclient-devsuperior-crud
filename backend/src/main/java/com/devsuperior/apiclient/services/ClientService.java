package com.devsuperior.apiclient.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.apiclient.dto.ClientDTO;
import com.devsuperior.apiclient.entities.Client;
import com.devsuperior.apiclient.repositories.ClientRepository;
import com.devsuperior.apiclient.services.exceptions.DataBaseException;
import com.devsuperior.apiclient.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(x -> new ClientDTO(x));
	}
	
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado!"));
		return new ClientDTO(entity);
	}


	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}
	
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
				@SuppressWarnings("deprecation")
				Client entity = repository.getOne(id);
				entity.setName(dto.getName());
				entity = repository.save(entity);
				return new ClientDTO(entity);
		}catch(EntityNotFoundException e ) {
			throw new ResourceNotFoundException("Id não encontrado " + id);
		}
	}


	public void delete(long id) {
		try {
			repository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encontrado " + id);
		}catch(DataIntegrityViolationException e) {
			throw new DataBaseException("A integridade não pode ser violada");
		}
	}
}