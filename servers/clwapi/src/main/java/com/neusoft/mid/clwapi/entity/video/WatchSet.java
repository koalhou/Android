package com.neusoft.mid.clwapi.entity.video;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
/**
 * @author wyg
 */
public class WatchSet implements Serializable {

	private static final long serialVersionUID = 2206941519353487502L;
	
	
	@XmlElement(name = "uid")
	public String UID;
	@XmlElement(name = "in_wifi")
	public String inWifi;
	@XmlElement(name = "play_time")
	public String playTime;

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public String getInWifi() {
		return inWifi;
	}

	public void setInWifi(String inWifi) {
		this.inWifi = inWifi;
	}

	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	@Override
	public String toString() {
		return "WatchSet [UID=" + UID + ", inWifi=" + inWifi + ", playTime="
				+ playTime + "]";
	}

}
