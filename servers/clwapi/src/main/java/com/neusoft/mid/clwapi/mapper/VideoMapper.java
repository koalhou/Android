
package com.neusoft.mid.clwapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.neusoft.mid.clwapi.entity.video.BusVideoStatus;
import com.neusoft.mid.clwapi.entity.video.VideoCarTree;
import com.neusoft.mid.clwapi.entity.video.WatchSet;

/**
 * @author wyg
 */
public interface VideoMapper {

	WatchSet getWatchSet(@Param(value = "user_id") String userId)throws DataAccessException;

	void add(WatchSet set)throws DataAccessException;
	
	void update(WatchSet set)throws DataAccessException;

	BusVideoStatus getBusVideoStatus(@Param(value = "vin") String vin)throws DataAccessException ;

	List<VideoCarTree> getVideoBusTree(@Param(value = "ent")String epid,@Param(value = "org") String orgID,@Param(value = "brands")List<String> brands)throws DataAccessException;

	
}
