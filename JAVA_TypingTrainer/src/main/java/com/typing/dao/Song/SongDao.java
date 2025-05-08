package com.typing.dao.Song;

import java.util.List;

import com.typing.model.dto.SongDto;
import com.typing.model.dto.SongGameSetting;
import com.typing.model.dto.SongRecordDTO;

public interface SongDao {
	List<SongDto> getRandomSongsByGenre(SongGameSetting setting);
	
	void saveRecord(SongRecordDTO record);
}
