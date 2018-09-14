package com.nbc.convergencerepo.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nbc.convergencerepo.domain.admin.RatingStream;
import com.nbc.convergencerepo.repository.admin.RatingStreamRepository;

@Service
public class RatingStreamService {
	@Autowired
	RatingStreamRepository rsRepo;

	public List<RatingStream> listAll() {
		return rsRepo.findAll();
	}

	public List<RatingStream> listActive() {
		return rsRepo.findAll(new RatingStream().listActive());
	}

	public void saveRatingStream(RatingStream rs) {
		try {
			rsRepo.save(rs);
		} catch (Exception e) {
			throw e;
		}
	}

	public void save(List<RatingStream> rsList) {
		try {
			rsRepo.save(rsList);
		} catch (Exception e) {
			throw e;
		}
	}

	public void deleteRatingStream(RatingStream rs) {
		try {
			rsRepo.delete(rs);
		} catch (Exception e) {
			throw e;
		}
	}

	public void deleteAll() {
		try {
			rsRepo.deleteAll();
		} catch (Exception e) {
			throw e;
		}
	}
}
