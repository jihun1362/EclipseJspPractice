package com.project.my.bean;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TestBean {

	private int id;
	private String title;
	private String content;
	private String writer;
	private String wdate;
	private String route;
	private String img;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(Timestamp wdate) {

		String msg = null;
		Instant instant = wdate.toInstant();
		LocalDateTime dayBefore = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		long gap = ChronoUnit.MINUTES.between(dayBefore, LocalDateTime.now());
		gap = gap + (60 * 9) - 1;
		if (gap == 0) {
			msg = "방금 전";
		} else if (gap < 60) {
			msg = gap + "분 전";
		} else if (gap < 60 * 24) {
			msg = (gap / 60) + "시간 전";
		} else if (gap < 60 * 24 * 10) {
			msg = (gap / 60 / 24) + "일 전";
		} else {
			msg = dayBefore.format(DateTimeFormatter.ofPattern("YY년 MM월 dd일"));
		}

		this.wdate = msg;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}