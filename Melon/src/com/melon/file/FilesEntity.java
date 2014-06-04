package com.melon.file;

/**
 * 与external.db数据库中files表映射实体类
 * @Description: TODO
 * @author maozhou
 * @date 2013-10-24 下午6:21:40
 */
public class FilesEntity {
	
	public boolean isSelected=false; //是否选择
	
	public Integer _id; //文件id
	public String _data; //文件路径
	public int _size; //文件大小
	public int format;
	public int parent;
	public Integer date_added;
	public Integer date_modified;
	public String mime_type; 
	public String title; 
	public String description;
	public String _display_name; //显示名称
	public String picasa_id; 
	public int orientation;
	public double latitude;
	public double longitude;
	public Integer datetaken;
	public int mini_thumb_magic; 
	public String bucket_id;
	public String bucket_display_name;
	public int isprivate;
	public String title_key;
	public int artist_id;
	public int album_id;
	public String composer;
	public int track;
	public Integer year;
	public int is_ringtone;
	public int is_music;
	public int is_alarm;
	public int is_notification;
	public int is_podcast;
	public String album_artist;
	public int duration;
	public int bookmark;
	public String artist;
	public String album;
	public String resolution;
	public String tags;
	public String category;
	public String language;
	public String mini_thumb_data;
	public String name;
	public int media_type;
	public int old_id;
	public int storage_id;
	public int is_drm;
	public int width;
	public int height;
	
}
