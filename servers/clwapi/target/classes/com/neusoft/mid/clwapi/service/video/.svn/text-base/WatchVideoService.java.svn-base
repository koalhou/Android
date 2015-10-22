package com.neusoft.mid.clwapi.service.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.entity.video.WatchSet;
import com.neusoft.mid.clwapi.mapper.VideoMapper;

@Service
public class WatchVideoService {
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);
	@Autowired
	private VideoMapper videoMapper;
	
	@Transactional
	public  void save(WatchSet set){
			videoMapper.update(set);
	}

	@Transactional
	public void add(WatchSet set) {
		videoMapper.add(set);
	}
}
